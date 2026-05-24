# Case Study: BDD Framework Development & Multi-Browser Stability

## Project Information

| Field | Details |
|---|---|
| **Project** | FoodKart Automation Framework |
| **Author** | Automation Architect |
| **Date** | 2026-05-23 |

---

# 1. Executive Summary

This document outlines the architectural journey of the **FoodKart E2E automation framework**.

It documents:

- The transition to a **BDD (Behavior-Driven Development)** structure using `playwright-bdd`
- Technical hurdles related to asynchronous state management
- The feasibility matrix for cross-browser testing
- Engineering decisions taken to stabilize CI/CD execution

---

# 2. Structural & Architectural Challenges

## A. Dependency Management & BDD Integration

### Challenge
Initial setup faced critical module resolution failures when attempting to interface legacy Cucumber libraries with modern Node.js runtimes.

### Root Cause
Older Cucumber adapters introduced compatibility conflicts with:
- Modern ESM/CommonJS handling
- Updated TypeScript compilation flows
- Current Node.js dependency resolution strategies

### Resolution
The framework was refactored to utilize:

```bash
playwright-bdd v7
```

This version:
- Removes dependency on legacy external Cucumber runtimes
- Uses a native TypeScript-based BDD parser
- Improves execution stability
- Simplifies CI/CD integration
- Reduces package dependency conflicts

### Outcome
- Stable test execution across environments
- Cleaner TypeScript integration
- Improved maintainability
- Faster pipeline setup and execution

---

## B. Cross-Origin Redirect Synchronization

### Challenge
During checkout execution, the application redirects from:
- FoodKart application domain
- → Stripe-hosted checkout domain

Tests frequently timed out because lifecycle listeners such as:

```ts
waitForLoadState('domcontentloaded')
```

were firing inconsistently across browser engines.

### Root Cause
Cross-origin redirects create unstable synchronization points because:
- Different browser engines trigger page lifecycle events differently
- Stripe embeds asynchronous rendering flows
- Network timing varies heavily during iframe rendering

### Resolution
The synchronization strategy was redesigned.

Instead of relying on:
- URL matching
- Page lifecycle listeners
- Hardcoded waits

The framework now waits for stable business-state UI elements.

### Implemented Strategy

Assertions were added for:
- `Total Amount`
- Checkout submit button visibility

Example:

```ts
await expect(totalAmount).toBeVisible();
await expect(payButton).toBeVisible();
```

### Outcome
The framework now synchronizes with:
- Actual application readiness
- Rendered checkout state
- Stable UI conditions

instead of brittle network events.

### Benefits
- Reduced flaky failures
- Improved execution reliability
- Cross-engine stability improvements
- Faster test stabilization

---

## C. Strict Mode Violations

### Challenge
Dynamic UI updates caused the following Playwright locator issue:

```ts
locator('text="Pay"')
```

This locator resolved to multiple elements:
- Header text
- Checkout submit button

Resulting in:

```txt
Strict mode violation
```

### Root Cause
The checkout page dynamically updates:
- Cart totals
- Payment labels
- Header content

This created duplicate text nodes containing `"Pay"`.

### Resolution
The locator strategy was redesigned to use Stripe’s dedicated testing attribute:

```html
data-testid="hosted-payment-submit-button"
```

### Updated Locator

```ts
page.getByTestId('hosted-payment-submit-button')
```

### Outcome
The locator became:
- Text independent
- Stable across UI updates
- Resistant to dynamic cart changes
- Reliable for multi-item orders

### Benefits
- Eliminated strict mode violations
- Improved selector maintainability
- Reduced flaky locator failures

---

# 3. Automation Feasibility & Browser Support Matrix

The framework supports four primary business scenarios:

1. Single Static Item
2. Multiple Static Items
3. Single Dynamic Item
4. Multiple Dynamic Items

---

## Browser Compatibility Matrix

| Scenario | Chromium | Firefox (Gecko) | WebKit (Safari) |
|---|---|---|---|
| **Single Static Item** | ✅ Feasible | ❌ Blocked (ITP/ETP) | ❌ Blocked (ITP/ETP) |
| **Multiple Static Items** | ✅ Feasible | ❌ Blocked (ITP/ETP) | ❌ Blocked (ITP/ETP) |
| **Single Dynamic Item** | ✅ Feasible | ❌ Blocked (ITP/ETP) | ❌ Blocked (ITP/ETP) |
| **Multiple Dynamic Items** | ✅ Feasible | ❌ Blocked (ITP/ETP) | ❌ Blocked (ITP/ETP) |

---

# 4. Engineering Justification for Browser Limitations

## A. Non-Feasible Engines (Firefox & WebKit)

### Technical Context

The framework itself is:
- Data agnostic
- Scenario independent
- Browser abstracted through Playwright APIs

However, the payment integration introduces environmental constraints outside framework control.

---

## Security Constraints

### Firefox — Enhanced Tracking Protection (ETP)

Mozilla Firefox enables:

```txt
Enhanced Tracking Protection (ETP)
```

which aggressively blocks:
- Third-party tracking behaviors
- Cross-origin iframe communication
- Embedded payment session propagation

---

### WebKit/Safari — Intelligent Tracking Prevention (ITP)

Safari/WebKit applies:

```txt
Intelligent Tracking Prevention (ITP)
```

which restricts:
- Third-party cookies
- Cross-site session persistence
- Embedded iframe trust relationships

---

## Impact on Stripe Checkout

During payment submission:
- The Stripe iframe communication channel is interrupted
- Session synchronization breaks
- The checkout enters a perpetual processing state

Observed symptom:

```txt
Processing...
```

with no completion callback returned.

---

## Engineering Decision

To preserve:
- Pipeline reliability
- CI/CD signal quality
- Deterministic execution

all checkout scenarios on:
- Firefox
- WebKit

are skipped intentionally.

### Implementation

A global Playwright `Before` hook excludes unsupported engines.

Example:

```ts
Before(async ({ browserName }) => {
  if (browserName === 'firefox' || browserName === 'webkit') {
    test.skip();
  }
});
```

---

## Business Rationale

This decision:
- Prevents false negatives
- Eliminates environmental noise
- Keeps reporting trustworthy
- Ensures engineering focus remains on actual application defects

---

# 5. Scope of Automation

## Chromium Support

All four scenarios are fully automatable on Chromium:

- Single Static
- Multiple Static
- Single Dynamic
- Multiple Dynamic

---

## Framework Strengths

The framework architecture provides:

### BDD Layer
- Human-readable feature specifications
- Business-aligned scenarios

### POM Architecture
- Reusable page abstractions
- Centralized locator maintenance

### Dynamic Data Handling
- Runtime-driven item selection
- Data-independent execution logic

### Stable Synchronization
- UI-state-driven waiting mechanisms
- Reduced timing flakiness

---

## Key Engineering Advantage

The automation logic relies on:
- Stable element interaction patterns
- Semantic selectors
- Application state assertions

instead of:
- Hardcoded timing
- Static datasets
- Fragile network synchronization

---

# 6. Final Conclusion

The FoodKart automation framework evolved into a stable, scalable, and enterprise-ready BDD solution by:

- Migrating to `playwright-bdd`
- Re-architecting synchronization strategies
- Implementing resilient locator patterns
- Introducing browser-specific execution governance

While Firefox and WebKit remain environmentally constrained due to modern browser privacy protections, Chromium execution provides:

- High-confidence regression coverage
- Stable payment flow automation
- Reliable CI/CD integration
- Maintainable long-term automation architecture

The resulting framework successfully balances:
- Stability
- Scalability
- Maintainability
- Real-world browser constraints
- Business execution confidence

---