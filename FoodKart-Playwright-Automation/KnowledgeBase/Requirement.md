# Requirements Document — FoodKart Web Application

**Project:** FoodKart — Online Food Ordering Platform  
**URL:** <https://food-kart-tau.vercel.app/>  
**Document Version:** 1.0  
**Prepared By:** Senior QA Engineer  
**Date:** 2026-05-20  

---

## 1. Project Overview

FoodKart is a web-based food ordering platform that allows users to browse a menu of food items, add items to a cart, adjust quantities, and proceed to checkout. The application is built as a single-page application (SPA) with multiple routes and supports lightning-fast delivery from local kitchens.

---

## 2. Functional Requirements

### 2.1 Landing Page (`/`)

|ID|Requirement|
|---|-----------|
|FR-01|The landing page SHALL display the FoodKart brand name and logo.|
|FR-02|The landing page SHALL display a promotional tagline ("Feeling Hungry!!") and a sub-description.|
|FR-03|The landing page SHALL display a "Lightning Fast Delivery" badge.|
|FR-04|The landing page SHALL contain an "Order Now" call-to-action button.|
|FR-05|Clicking "Order Now" SHALL navigate the user to the `/menu` page.|
|FR-06|The landing page SHALL display a hero image of a food item (burger).|

### 2.2 Menu Page (`/menu`)

|ID|Requirement|
|---|-----------|
|FR-07|The menu page SHALL display a heading "What's on your mind?" and a description.|
|FR-08|The menu page SHALL display a search bar with placeholder text "Search for dishes, cuisines...".|
|FR-09|The search bar SHALL filter displayed food items based on the user's input in real time.|
|FR-10|The menu page SHALL display all available food items as cards in a grid layout.|
|FR-11|Each food item card SHALL display: name, price (in ₹), cuisine tags, star rating, and food safety/delivery icons.|
|FR-12|Each food item card SHALL contain an "Add to Cart" button.|
|FR-13|Clicking "Add to Cart" SHALL add the item to the user's cart and update the cart icon badge in the header.|
|FR-14|The following food items SHALL be available in the menu: Butter Chicken with Biryani (₹350), Vada Pav (₹50), Margherita Pizza (₹70), Steamed Momos (₹70), Chole Kulche (₹210), Crispy Spring Rolls (₹100), Chole Bhature (₹300), Masala Chai (₹100), Chicken Frankie Roll (₹100).|
|FR-15|Each food item SHALL display its rating (e.g., 3.8★, 4.2★).|

### 2.3 Cart Page (`/cart`)

|ID|Requirement|
|---|-----------|
|FR-16|The cart page SHALL display the heading "Your Cart (n)" where `n` is the total number of items.|
|FR-17|Each cart line item SHALL show: product image, product name, unit price (₹ / item), quantity controls, and line total.|
|FR-18|The cart SHALL provide "+" (increment) and "−" (decrement) buttons to adjust item quantity.|
|FR-19|Incrementing quantity SHALL increase the line total and Order Summary total accordingly.|
|FR-20|Decrementing quantity to zero SHALL remove the item from the cart.|
|FR-21|The cart page SHALL display an Order Summary section showing: Total Items, Delivery Fee, and Total Amount.|
|FR-22|Delivery Fee SHALL be displayed as "Free".|
|FR-23|The "Proceed to Checkout" button SHALL be present and accessible.|
|FR-24|A "Clear All" button SHALL be present and, when clicked, SHALL remove all items from the cart.|
|FR-25|The cart item count badge on the header cart icon SHALL reflect the actual number of items in the cart.|

### 2.4 Navigation / Header

|ID|Requirement|
|---|-----------|
|FR-26|The header SHALL be present on all pages (Menu, Cart).|
|FR-27|The FoodKart logo/brand in the header SHALL be a link navigating back to the landing page (`/`).|
|FR-28|The cart icon in the header SHALL display a badge showing the current item count.|
|FR-29|Clicking the cart icon SHALL navigate the user to `/cart`.|

---

## 3. Non-Functional Requirements

|ID|Requirement|
|---|-----------|
|NFR-01|The application SHALL load the landing page within 3 seconds on a standard broadband connection.|
|NFR-02|The application SHALL be responsive and usable on desktop, tablet, and mobile screen sizes.|
|NFR-03|The application SHALL be accessible and pass basic WCAG 2.1 Level AA checks (alt text, button labels, heading hierarchy).|
|NFR-04|The application SHALL use HTTPS for all traffic.|
|NFR-05|The application SHALL maintain cart state within the same browser session (state persistence via local/session storage or React state).|
|NFR-06|The application SHALL display correct currency formatting using the Indian Rupee symbol (₹).|
|NFR-07|All images SHALL include descriptive `alt` attributes for accessibility.|
|NFR-08|Page titles SHALL be descriptive and unique per route (e.g., "Food-Kart | Best Meals in Town", "Explore Menus | Food-Kart", "Checkout Your Cart | Food-Kart").|

---

## 4. User Interface Requirements

|ID|Requirement|
|---|-----------|
|UIR-01|The color scheme SHALL use a dark navy/black primary with green accent (#00c37a or similar) for CTAs.|
|UIR-02|The "Add to Cart" button SHALL change its visual style after an item is added (e.g., highlighted/green).|
|UIR-03|The cart badge SHALL use a red/orange indicator to attract user attention.|
|UIR-04|Food item cards SHALL display cuisine category tags below the dish name.|

---

## 5. Out of Scope (Current Version)

- User authentication / login / registration
- Payment gateway integration
- Order tracking post-checkout
- User reviews or ratings submission
- Restaurant management portal

---

## 6. Assumptions

- The application is a front-end prototype; no real backend order processing is performed.
- Delivery fee is always free for all orders.
- Cart state does not persist across browser refreshes (session-level state only).
- All prices are in Indian Rupees (INR).
