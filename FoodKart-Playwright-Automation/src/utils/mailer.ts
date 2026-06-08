import * as nodemailer from 'nodemailer';
import * as fs from 'fs';
import * as path from 'path';
import 'dotenv/config';

interface JSONReportSpec {
  ok: boolean;
  tests: Array<{ status: string }>;
}

interface JSONReportSuite {
  specs: JSONReportSpec[];
  suites?: JSONReportSuite[];
}

interface JSONReportRoot {
  suites: JSONReportSuite[];
}

async function sendTestReport(): Promise<void> {
    // 1. Pull credentials safely from environment variables
    const userEmail: string | undefined = process.env.EMAIL_USER;
    const userPass: string | undefined = process.env.EMAIL_PASS;

    if (!userEmail || !userPass) {
        console.error('❌ FATAL: Email credentials not found. Did you forget your .env file or CI secrets?');
        process.exit(1);
    }

    // 2. Read the Playwright JSON report (looking at project root)
    const reportPath: string = path.join(process.cwd(), 'test-results.json');
    if (!fs.existsSync(reportPath)) {
        console.error('❌ No test-results.json found. Did the test suite actually run?');
        process.exit(1);
    }

    const rawData: string = fs.readFileSync(reportPath, 'utf8');
    const report: JSONReportRoot = JSON.parse(rawData);

    // 3. Calculate Statistics
    let passed: number = 0;
    let failed: number = 0;
    let skipped: number = 0;
    let total: number = 0;

    // Helper function to recursively traverse nested Gherkin suites
    const extractSpecs = (suite: JSONReportSuite): JSONReportSpec[] => {
        let specs: JSONReportSpec[] = suite.specs || [];
        if (suite.suites) {
            suite.suites.forEach(nested => {
                specs = specs.concat(extractSpecs(nested));
            });
        }
        return specs;
    };

    report.suites.forEach((mainSuite: JSONReportSuite) => {
        const allSpecs = extractSpecs(mainSuite);
        
        allSpecs.forEach((spec: any) => {
            total++;
            const testResult = spec.tests && spec.tests.length > 0 ? spec.tests[0] : null;
            
            if (testResult && testResult.status === 'skipped') {
                skipped++;
            } else if (!spec.ok) {
                failed++; // If ok is false, it's a hard failure
            } else {
                passed++; // If ok is true and it wasn't skipped, it passed
            }
        });
    });

    const passPercentage: string = total > 0 ? ((passed / total) * 100).toFixed(2) : "0.00";
    
    // 4. Build the HTML Email Template (Modern SaaS Style)
    const primaryColor = failed > 0 ? '#EF4444' : '#10B981'; // Red or Emerald Green
    const lightBgColor = failed > 0 ? '#FEF2F2' : '#ECFDF5'; // Light red or green tint
    const borderColor = failed > 0 ? '#FCA5A5' : '#6EE7B7';

    const htmlTemplate: string = `
        <div style="font-family: 'Segoe UI', Roboto, Helvetica, Arial, sans-serif; background-color: #F3F4F6; padding: 40px 20px; color: #1F2937;">
            
            <div style="max-width: 600px; margin: 0 auto; background-color: #FFFFFF; border-radius: 12px; overflow: hidden; border: 1px solid #E5E7EB; box-shadow: 0 4px 6px rgba(0, 0, 0, 0.05);">
                
                <div style="background-color: #111827; padding: 30px 20px; text-align: center;">
                    <h1 style="margin: 0; font-size: 22px; font-weight: 600; color: #FFFFFF; letter-spacing: -0.5px;">FoodKart Test Execution</h1>
                    <p style="margin: 8px 0 0 0; font-size: 15px; color: #9CA3AF;">Automated Pipeline Report</p>
                </div>

                <div style="background-color: ${lightBgColor}; border-bottom: 1px solid ${borderColor}; padding: 15px; text-align: center;">
                    <h2 style="margin: 0; color: ${primaryColor}; font-size: 20px; text-transform: uppercase; letter-spacing: 1px;">
                        ${failed > 0 ? '❌ Pipeline Failed' : '✅ Pipeline Passed'}
                    </h2>
                </div>

                <div style="padding: 30px 20px;">
                    <table style="width: 100%; border-collapse: collapse; text-align: center;">
                        <tr>
                            <td style="padding: 15px; width: 50%; border-right: 1px solid #E5E7EB; border-bottom: 1px solid #E5E7EB;">
                                <p style="margin: 0; font-size: 12px; font-weight: 600; color: #6B7280; text-transform: uppercase; letter-spacing: 0.5px;">Total Tests</p>
                                <p style="margin: 8px 0 0 0; font-size: 32px; font-weight: bold; color: #1F2937;">${total}</p>
                            </td>
                            <td style="padding: 15px; width: 50%; border-bottom: 1px solid #E5E7EB;">
                                <p style="margin: 0; font-size: 12px; font-weight: 600; color: #6B7280; text-transform: uppercase; letter-spacing: 0.5px;">Pass Rate</p>
                                <p style="margin: 8px 0 0 0; font-size: 32px; font-weight: bold; color: ${primaryColor};">${passPercentage}%</p>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding: 15px; width: 50%; border-right: 1px solid #E5E7EB;">
                                <p style="margin: 0; font-size: 12px; font-weight: 600; color: #10B981; text-transform: uppercase; letter-spacing: 0.5px;">Passed</p>
                                <p style="margin: 8px 0 0 0; font-size: 24px; font-weight: bold; color: #10B981;">${passed}</p>
                            </td>
                            <td style="padding: 15px; width: 50%;">
                                <p style="margin: 0; font-size: 12px; font-weight: 600; color: #EF4444; text-transform: uppercase; letter-spacing: 0.5px;">Failed</p>
                                <p style="margin: 8px 0 0 0; font-size: 24px; font-weight: bold; color: #EF4444;">${failed}</p>
                            </td>
                        </tr>
                    </table>

                    ${skipped > 0 ? `
                    <div style="margin-top: 20px; text-align: center; background-color: #FEF3C7; border: 1px solid #FCD34D; border-radius: 6px; padding: 10px;">
                        <p style="margin: 0; font-size: 14px; color: #B45309; font-weight: 500;">⚠️ ${skipped} test(s) skipped</p>
                    </div>` : ''}
                </div>

                <div style="background-color: #F9FAFB; padding: 20px; text-align: center; border-top: 1px solid #E5E7EB;">
                    <p style="margin: 0; font-size: 13px; color: #6B7280; line-height: 1.5;">Check the CI/CD pipeline run details for failure tracebacks and execution videos.</p>
                    <p style="margin: 10px 0 0 0; font-size: 12px; color: #D1D5DB; text-transform: uppercase; letter-spacing: 1px;">Playwright TS Bot</p>
                </div>

            </div>
        </div>
    `;

    // 5. Configure the SMTP Transporter
    const transporter = nodemailer.createTransport({
        service: 'gmail',
        auth: { user: userEmail, pass: userPass }
    });

    // 6. Send the Email
    try {
        const info = await transporter.sendMail({
            from: `"Playwright TS Bot" <${userEmail}>`,
            to: userEmail, 
            subject: `Automated Test Results: ${failed > 0 ? 'FAILED ❌' : 'PASSED ✅'} (${passPercentage}%)`,
            html: htmlTemplate,
        });
        console.log(`✅ TypeScript Email report sent successfully: ${info.messageId}`);
    } catch (error) {
        console.error('❌ Error sending email via TypeScript runner:', error);
    }
}

sendTestReport();