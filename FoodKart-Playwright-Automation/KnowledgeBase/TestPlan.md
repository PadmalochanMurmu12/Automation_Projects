# Test Plan — FoodKart Web Application

**Project:** FoodKart — Online Food Ordering Platform  
**URL:** https://food-kart-tau.vercel.app/  
**Document Version:** 1.0  
**Prepared By:** Senior QA Engineer  
**Date:** 2026-05-20  

---

## 1. Introduction

This Test Plan describes the strategy, scope, resources, and schedule for testing the FoodKart web application. The goal is to verify that all functional, non-functional, and UI requirements are met, and to identify defects before production release.

---

## 2. Test Objectives

- Validate all functional requirements defined in `Requirement.md`.
- Verify end-to-end user flows: Browse → Add to Cart → Review Cart → Checkout.
- Identify UI/UX inconsistencies and accessibility issues.
- Confirm responsive behavior across desktop, tablet, and mobile viewports.
- Ensure cross-browser compatibility on major browsers.

---

## 3. Scope

### 3.1 In Scope

| Area | Details |
|------|---------|
| Landing Page | Layout, CTA button, navigation |
| Menu Page | Item listing, search/filter, Add to Cart |
| Cart Page | Quantity controls, price calculation, Clear All, Proceed to Checkout |
| Header | Navigation links, cart badge count |
| Responsiveness | Desktop (1280px+), Tablet (768px), Mobile (375px) |
| Browsers | Chrome, Firefox, Edge, Safari |
| Accessibility | Alt text, heading hierarchy, keyboard navigation |

### 3.2 Out of Scope

- Payment processing / checkout completion flow (back-end not implemented)
- Performance / load testing beyond page-load timings
- Security penetration testing
- Back-end API testing (application uses static/mock data)

---

## 4. Test Types

| Type | Description |
|------|-------------|
| Functional Testing | Verify each feature works as per requirements |
| UI/Visual Testing | Validate layout, colors, typography, icons |
| Regression Testing | Re-run test suite after any code change |
| Usability Testing | Ensure intuitive UX and clear CTAs |
| Boundary/Negative Testing | Edge cases — empty cart, zero quantity, invalid search |
| Accessibility Testing | WCAG 2.1 Level AA checks |
| Cross-Browser Testing | Chrome, Firefox, Edge, Safari |
| Responsive Testing | Multiple viewport sizes |

---

## 5. Test Environment

| Parameter | Value |
|-----------|-------|
| Application URL | https://food-kart-tau.vercel.app/ |
| Environment | Production (Vercel hosted) |
| Browsers | Chrome 124+, Firefox 125+, Edge 124+, Safari 17+ |
| Devices | Desktop (Windows/Mac), Android, iOS |
| Screen Resolutions | 1440×900, 1280×800, 768×1024, 375×812 |
| Network | Standard broadband (50 Mbps) |
| Tools | Playwright (automation), Chrome DevTools, Lighthouse, axe DevTools |

---

## 6. Test Cases

---

### Module 1: Landing Page

#### TC-001 — Landing Page Load & Content

| Field | Detail |
|-------|--------|
| **Test Case ID** | TC-001 |
| **Title** | Verify landing page loads with all required elements |
| **Precondition** | User navigates to https://food-kart-tau.vercel.app/ |
| **Steps** | 1. Open the URL in a browser. 2. Observe all visible elements. |
| **Expected Result** | Page loads with: FoodKart brand name + logo, "Lightning Fast Delivery" badge, "Feeling Hungry!!" heading, descriptive paragraph, "Order Now" button, and hero burger image. |
| **Requirement** | FR-01 to FR-06 |
| **Priority** | High |

#### TC-002 — Order Now Navigation

| Field | Detail |
|-------|--------|
| **Test Case ID** | TC-002 |
| **Title** | Verify "Order Now" button navigates to menu page |
| **Precondition** | User is on the landing page |
| **Steps** | 1. Click the "Order Now" button. |
| **Expected Result** | Browser navigates to `/menu`. Page title changes to "Explore Menus \| Food-Kart". |
| **Requirement** | FR-05 |
| **Priority** | High |

---

### Module 2: Menu Page

#### TC-003 — Menu Page Content

| Field | Detail |
|-------|--------|
| **Test Case ID** | TC-003 |
| **Title** | Verify all 9 food items are displayed on the menu page |
| **Precondition** | User is on `/menu` |
| **Steps** | 1. Count all food item cards. 2. Verify each card shows name, price, cuisine tags, rating, and "Add to Cart" button. |
| **Expected Result** | 9 food item cards are displayed. Each card contains all required elements. |
| **Requirement** | FR-10, FR-11, FR-14 |
| **Priority** | High |

#### TC-004 — Search / Filter Functionality

| Field | Detail |
|-------|--------|
| **Test Case ID** | TC-004 |
| **Title** | Verify search bar filters menu items in real time |
| **Precondition** | User is on `/menu` |
| **Steps** | 1. Type "pizza" in the search bar. 2. Observe displayed cards. 3. Clear search, type "momos". 4. Observe displayed cards. |
| **Expected Result** | Step 2: Only "Margherita Pizza" is displayed. Step 4: Only "Steamed Momos" is displayed. |
| **Requirement** | FR-08, FR-09 |
| **Priority** | High |

#### TC-005 — Search No Results

| Field | Detail |
|-------|--------|
| **Test Case ID** | TC-005 |
| **Title** | Verify appropriate message when search returns no results |
| **Precondition** | User is on `/menu` |
| **Steps** | 1. Type "xyzabc123" in the search bar. |
| **Expected Result** | No food item cards are shown. A "no results" or empty state message is displayed. |
| **Requirement** | FR-09 |
| **Priority** | Medium |

#### TC-006 — Add to Cart from Menu

| Field | Detail |
|-------|--------|
| **Test Case ID** | TC-006 |
| **Title** | Verify "Add to Cart" adds item and updates cart badge |
| **Precondition** | User is on `/menu`. Cart is empty. |
| **Steps** | 1. Click "Add to Cart" on "Vada Pav". 2. Observe the cart icon badge in the header. |
| **Expected Result** | Cart badge updates to "1". Item is added to the cart. |
| **Requirement** | FR-12, FR-13, FR-25 |
| **Priority** | High |

#### TC-007 — Add Multiple Items to Cart

| Field | Detail |
|-------|--------|
| **Test Case ID** | TC-007 |
| **Title** | Verify adding multiple different items updates cart badge correctly |
| **Precondition** | User is on `/menu`. Cart is empty. |
| **Steps** | 1. Add "Vada Pav". 2. Add "Margherita Pizza". 3. Add "Masala Chai". |
| **Expected Result** | Cart badge shows "3" after all additions. |
| **Requirement** | FR-13, FR-25 |
| **Priority** | High |

---

### Module 3: Cart Page

#### TC-008 — Cart Page Displays Items

| Field | Detail |
|-------|--------|
| **Test Case ID** | TC-008 |
| **Title** | Verify cart page displays added items with correct details |
| **Precondition** | User has added "Butter Chicken with Biryani" (₹350) to cart. User navigates to `/cart`. |
| **Steps** | 1. Observe the cart page. |
| **Expected Result** | Cart shows "Your Cart (1)". Line item shows image, name "Butter Chicken with Biryani", "₹350 / item", quantity "1", line total "₹350". Order Summary shows Total Items: 1, Delivery Fee: Free, Total Amount: ₹350. |
| **Requirement** | FR-16, FR-17, FR-21, FR-22 |
| **Priority** | High |

#### TC-009 — Increment Item Quantity

| Field | Detail |
|-------|--------|
| **Test Case ID** | TC-009 |
| **Title** | Verify "+" button increments quantity and updates totals |
| **Precondition** | Cart has 1× Butter Chicken with Biryani (₹350). User is on `/cart`. |
| **Steps** | 1. Click the "+" button for Butter Chicken. |
| **Expected Result** | Quantity changes to 2. Line total updates to ₹700. Order Summary Total Amount updates to ₹700. Total Items shows 2. |
| **Requirement** | FR-18, FR-19 |
| **Priority** | High |

#### TC-010 — Decrement Item Quantity

| Field | Detail |
|-------|--------|
| **Test Case ID** | TC-010 |
| **Title** | Verify "−" button decrements quantity correctly |
| **Precondition** | Cart has 2× Butter Chicken with Biryani. User is on `/cart`. |
| **Steps** | 1. Click the "−" button. |
| **Expected Result** | Quantity reduces to 1. Line total updates to ₹350. Total Amount updates to ₹350. |
| **Requirement** | FR-18, FR-19 |
| **Priority** | High |

#### TC-011 — Remove Item by Decrementing to Zero

| Field | Detail |
|-------|--------|
| **Test Case ID** | TC-011 |
| **Title** | Verify item is removed when quantity decremented to zero |
| **Precondition** | Cart has 1× Vada Pav. User is on `/cart`. |
| **Steps** | 1. Click "−" button once. |
| **Expected Result** | Vada Pav is removed from the cart. Cart shows "Your Cart (0)" or an empty state. Total Amount updates to ₹0. |
| **Requirement** | FR-20 |
| **Priority** | High |

#### TC-012 — Clear All Button

| Field | Detail |
|-------|--------|
| **Test Case ID** | TC-012 |
| **Title** | Verify "Clear All" removes all items from cart |
| **Precondition** | Cart has multiple items. User is on `/cart`. |
| **Steps** | 1. Click "Clear All". |
| **Expected Result** | All items are removed. Cart shows empty state. Total Amount is ₹0. Cart badge in header updates to 0. |
| **Requirement** | FR-24 |
| **Priority** | High |

#### TC-013 — Delete Individual Item

| Field | Detail |
|-------|--------|
| **Test Case ID** | TC-013 |
| **Title** | Verify the delete (trash) icon removes only the specific item |
| **Precondition** | Cart has "Butter Chicken" and "Vada Pav". User is on `/cart`. |
| **Steps** | 1. Click the delete (🗑) icon next to "Vada Pav". |
| **Expected Result** | Only "Vada Pav" is removed. "Butter Chicken" remains. Totals update accordingly. |
| **Requirement** | FR-17 |
| **Priority** | High |

#### TC-014 — Proceed to Checkout Button

| Field | Detail |
|-------|--------|
| **Test Case ID** | TC-014 |
| **Title** | Verify "Proceed to Checkout" button is present and clickable |
| **Precondition** | Cart has at least 1 item. User is on `/cart`. |
| **Steps** | 1. Observe the "Proceed to Checkout" button. 2. Click it. |
| **Expected Result** | Button is visible and enabled. Click triggers checkout flow (navigation or confirmation). |
| **Requirement** | FR-23 |
| **Priority** | Medium |

---

### Module 4: Header / Navigation

#### TC-015 — Header Logo Navigation

| Field | Detail |
|-------|--------|
| **Test Case ID** | TC-015 |
| **Title** | Verify FoodKart logo navigates back to landing page |
| **Precondition** | User is on `/menu` or `/cart`. |
| **Steps** | 1. Click the "FoodKart 🍜" logo/brand in the header. |
| **Expected Result** | User is navigated to the landing page (`/`). |
| **Requirement** | FR-27 |
| **Priority** | Medium |

#### TC-016 — Cart Icon Navigation

| Field | Detail |
|-------|--------|
| **Test Case ID** | TC-016 |
| **Title** | Verify cart icon navigates to cart page |
| **Precondition** | User is on `/menu`. |
| **Steps** | 1. Click the cart icon in the header. |
| **Expected Result** | User is navigated to `/cart`. |
| **Requirement** | FR-29 |
| **Priority** | High |

#### TC-017 — Cart Badge Accuracy

| Field | Detail |
|-------|--------|
| **Test Case ID** | TC-017 |
| **Title** | Verify cart badge reflects correct item count |
| **Precondition** | Cart is empty. User is on `/menu`. |
| **Steps** | 1. Add 3 different items. Observe badge after each addition. |
| **Expected Result** | Badge shows 1, then 2, then 3 after each addition. |
| **Requirement** | FR-25 |
| **Priority** | High |

---

### Module 5: Non-Functional & Accessibility

#### TC-018 — Page Load Performance

| Field | Detail |
|-------|--------|
| **Test Case ID** | TC-018 |
| **Title** | Verify landing page loads within 3 seconds |
| **Precondition** | Standard broadband connection |
| **Steps** | 1. Open Chrome DevTools → Network tab. 2. Navigate to landing page. 3. Record load time. |
| **Expected Result** | Page fully loads (DOMContentLoaded + load) within 3000 ms. |
| **Requirement** | NFR-01 |
| **Priority** | Medium |

#### TC-019 — Responsive Design — Mobile

| Field | Detail |
|-------|--------|
| **Test Case ID** | TC-019 |
| **Title** | Verify all pages are usable on 375px mobile viewport |
| **Precondition** | Device emulation set to iPhone SE (375×667). |
| **Steps** | 1. Navigate to `/`, `/menu`, and `/cart`. Observe layout on each page. |
| **Expected Result** | Content is readable, no horizontal overflow, buttons are tappable (min 44×44px). |
| **Requirement** | NFR-02 |
| **Priority** | High |

#### TC-020 — Accessibility — Image Alt Text

| Field | Detail |
|-------|--------|
| **Test Case ID** | TC-020 |
| **Title** | Verify all images have descriptive alt attributes |
| **Precondition** | User navigates through all pages. |
| **Steps** | 1. Run axe DevTools or inspect images via DevTools. |
| **Expected Result** | All `<img>` elements have non-empty, descriptive `alt` attributes. |
| **Requirement** | NFR-07 |
| **Priority** | Medium |

#### TC-021 — Currency Formatting

| Field | Detail |
|-------|--------|
| **Test Case ID** | TC-021 |
| **Title** | Verify all prices use ₹ symbol and correct formatting |
| **Precondition** | User is on `/menu` and `/cart`. |
| **Steps** | 1. Review all price displays on menu and cart. |
| **Expected Result** | All prices display in the format "₹XXX" (e.g., ₹350). No dollar or other currency symbols present. |
| **Requirement** | NFR-06 |
| **Priority** | Low |

#### TC-022 — Page Titles

| Field | Detail |
|-------|--------|
| **Test Case ID** | TC-022 |
| **Title** | Verify unique and descriptive page titles per route |
| **Precondition** | None |
| **Steps** | 1. Navigate to `/` — check title. 2. Navigate to `/menu` — check title. 3. Navigate to `/cart` — check title. |
| **Expected Result** | `/` → "Food-Kart \| Best Meals in Town"; `/menu` → "Explore Menus \| Food-Kart"; `/cart` → "Checkout Your Cart \| Food-Kart". |
| **Requirement** | NFR-08 |
| **Priority** | Low |

---

## 7. Test Execution Summary Template

| TC ID | Title | Status | Defect ID | Notes |
|-------|-------|--------|-----------|-------|
| TC-001 | Landing page load | — | — | |
| TC-002 | Order Now navigation | — | — | |
| TC-003 | Menu item display | — | — | |
| TC-004 | Search filter | — | — | |
| TC-005 | Search no results | — | — | |
| TC-006 | Add to Cart (single) | — | — | |
| TC-007 | Add multiple items | — | — | |
| TC-008 | Cart item details | — | — | |
| TC-009 | Increment quantity | — | — | |
| TC-010 | Decrement quantity | — | — | |
| TC-011 | Remove by decrement | — | — | |
| TC-012 | Clear All | — | — | |
| TC-013 | Delete individual item | — | — | |
| TC-014 | Proceed to Checkout | — | — | |
| TC-015 | Logo navigation | — | — | |
| TC-016 | Cart icon navigation | — | — | |
| TC-017 | Cart badge accuracy | — | — | |
| TC-018 | Page load performance | — | — | |
| TC-019 | Mobile responsive | — | — | |
| TC-020 | Image alt text | — | — | |
| TC-021 | Currency formatting | — | — | |
| TC-022 | Page titles | — | — | |

**Status values:** Pass | Fail | Blocked | Not Run

---

## 8. Entry & Exit Criteria

### Entry Criteria
- Application is deployed and accessible at the target URL.
- Requirements document (`Requirement.md`) is reviewed and signed off.
- Test environment (browsers, devices) is configured and available.

### Exit Criteria
- All High priority test cases have been executed.
- No open Critical or High severity defects remain unresolved.
- At least 90% of all test cases have passed.
- Test Execution Summary is completed and shared with stakeholders.

---

## 9. Defect Management

Defects found during testing shall be logged with the following fields:

| Field | Description |
|-------|-------------|
| Defect ID | Unique identifier (e.g., DEF-001) |
| Title | Short description |
| Severity | Critical / High / Medium / Low |
| Priority | P1 / P2 / P3 |
| Steps to Reproduce | Numbered steps |
| Expected Result | What should happen |
| Actual Result | What actually happened |
| Screenshot/Evidence | Attachment |
| Status | Open / In Progress / Fixed / Closed |

---

## 10. Risks & Mitigations

| Risk | Likelihood | Impact | Mitigation |
|------|-----------|--------|-----------|
| Cart state lost on refresh | High | Medium | Document as known limitation; test session behavior explicitly |
| Checkout flow not implemented | High | Low | Mark TC-014 as out-of-scope for functional verification; check button existence only |
| Cross-browser CSS inconsistencies | Medium | Medium | Run visual checks on all target browsers |
| Search filter case sensitivity | Medium | Low | Test both uppercase and lowercase inputs |
| Mobile layout breakage | Medium | High | Use browser DevTools responsive mode + real device testing |