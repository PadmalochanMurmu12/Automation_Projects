import * as nodemailer from 'nodemailer';
import * as fs from 'fs';
import * as path from 'path';

// Define minimal interfaces for Playwright's JSON report structure
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
        console.error('❌ FATAL: Email credentials not found in environment variables.');
        process.exit(1);
    }

    // 2. Read the Playwright JSON report (looking at project root)
    const reportPath: string = path.join(process.cwd(), 'test-results.json');
    if (!fs.existsSync(reportPath)) {
        console.error('❌ No test-results.json found. Did the test suite run?');
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
        
        allSpecs.forEach((spec: JSONReportSpec) => {
            total++;
            if (spec.ok) {
                passed++;
            } else if (spec.tests[0]?.status === 'skipped') {
                skipped++;
            } else {
                failed++;
            }
        });
    });

    const passPercentage: string = total > 0 ? ((passed / total) * 100).toFixed(2) : "0.00";
    const statusColor: string = failed > 0 ? '#ff4d4f' : '#52c41a'; 

    // 4. Build the HTML Email Template
    const htmlTemplate: string = `
        <div style="font-family: Arial, sans-serif; max-width: 600px; margin: auto; border: 1px solid #ddd; border-radius: 8px; overflow: hidden;">
            <div style="background-color: ${statusColor}; color: white; padding: 20px; text-align: center;">
                <h2>🚀 FoodKart Automation Execution Report</h2>
                <p>Status: ${failed > 0 ? 'FAILED ❌' : 'PASSED ✅'}</p>
            </div>
            <div style="padding: 20px;">
                <h3 style="border-bottom: 1px solid #eee; padding-bottom: 10px;">Execution Statistics</h3>
                <table style="width: 100%; text-align: left; border-collapse: collapse;">
                    <tr style="background-color: #f9f9f9;">
                        <th style="padding: 10px; border: 1px solid #ddd;">Total Tests</th>
                        <td style="padding: 10px; border: 1px solid #ddd; font-weight: bold;">${total}</td>
                    </tr>
                    <tr>
                        <th style="padding: 10px; border: 1px solid #ddd; color: green;">Passed</th>
                        <td style="padding: 10px; border: 1px solid #ddd; font-weight: bold; color: green;">${passed}</td>
                    </tr>
                    <tr style="background-color: #f9f9f9;">
                        <th style="padding: 10px; border: 1px solid #ddd; color: red;">Failed</th>
                        <td style="padding: 10px; border: 1px solid #ddd; font-weight: bold; color: red;">${failed}</td>
                    </tr>
                    <tr>
                        <th style="padding: 10px; border: 1px solid #ddd; color: orange;">Skipped</th>
                        <td style="padding: 10px; border: 1px solid #ddd; font-weight: bold; color: orange;">${skipped}</td>
                    </tr>
                    <tr style="background-color: #f9f9f9;">
                        <th style="padding: 10px; border: 1px solid #ddd;">Pass Rate</th>
                        <td style="padding: 10px; border: 1px solid #ddd; font-weight: bold;">${passPercentage}%</td>
                    </tr>
                </table>
                <p style="margin-top: 20px; color: #555;">Please check the CI/CD pipeline run details for detailed failure tracebacks and execution videos.</p>
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