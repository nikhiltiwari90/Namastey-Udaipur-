# SETUP GUIDE — Publishing to GitHub
## Namastey Udaipur Portfolio Repository
*Read this before pushing to GitHub*

---

## Step 1: Replace All Placeholder Text

Search the entire repository for `Nikhil Tiwari`, `Nikhil Tiwari`, `nikhil.tiwari@namasteyudaipur.com`, and `linkedin.com/in/nikhiltiwari` and replace with your actual information.

Files to update:
- `LICENSE.md` — replace all 4 placeholders
- `README.md` — replace `Nikhil Tiwari`
- All `/docs/*.md` files — replace `Nikhil Tiwari`
- `demo/ProductDemoScript.md` — replace `Nikhil Tiwari`

---

## Step 2: Initialize Git Repository

```bash
cd namastey-udaipur
git init
git add .
git commit -m "Initial commit: Namastey Udaipur product portfolio"
```

---

## Step 3: Create GitHub Repository

1. Go to github.com → New Repository
2. Repository name: `namastey-udaipur`
3. Description: `Culturally-personalized travel companion for domestic Indian tourists — Product Portfolio (2015 Pilot + 2026 Vision)`
4. Set to **Public** (for recruiter visibility)
5. Do NOT initialize with README (you already have one)

---

## Step 4: Push to GitHub

```bash
git remote add origin https://github.com/nikhiltiwari/namastey-udaipur.git
git branch -M main
git push -u origin main
```

---

## Step 5: Configure Repository Settings on GitHub

**About section (top right of repo):**
- Description: `AI-powered culturally-personalized travel companion | Product Portfolio | PM case study`
- Website: Your portfolio URL or LinkedIn
- Topics: `product-management`, `ai-product`, `travel-tech`, `india`, `multilingual`, `portfolio`

**Pin this repository** to your GitHub profile for maximum recruiter visibility.

---

## Step 6: Add to LinkedIn

Post on LinkedIn:
> "Just published a deep-dive PM portfolio case study on Namastey Udaipur — a culturally-personalized travel app I co-founded in 2015 for domestic Indian tourists.
>
> The full repo includes: PRD, Working Backwards doc, JTBD analysis, KPI tree with SQL queries, architecture decisions, postmortem, and a 2026 AI vision for what it could become.
>
> The product insight: India has 22 languages. Every major travel app assumes everyone speaks English. A Tamil family visiting Udaipur shouldn't feel like foreigners in their own country.
>
> [GitHub link]
>
> #ProductManagement #AIProduct #TravelTech #Portfolio"

---

## Copyright Protection Notes

The `LICENSE.md` file in this repository:
- Asserts full copyright ownership
- Prohibits implementation of product concepts without permission
- Permits reading for hiring/evaluation purposes only
- References applicable Indian and international IP law

For additional protection, consider:
1. **Trademark:** Register "Namastey Udaipur" as a trademark with IP India (ipindia.gov.in) — Class 39 (travel services) and Class 42 (software services)
2. **Copyright registration:** File with Copyright Office India for the literary works (docs) — nominal fee
3. **Date stamping:** The git commit history will serve as evidence of creation date

---

## Repository Structure Verification

Run this to confirm all files are present before pushing:

```bash
find . -name "*.md" | sort
```

Expected output should include all files listed in README.md's repository structure section.
