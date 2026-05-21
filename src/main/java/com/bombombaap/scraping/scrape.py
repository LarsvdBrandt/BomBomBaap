from playwright.sync_api import sync_playwright
import time
import json

results = []

with sync_playwright() as p:
    browser = p.chromium.launch(headless=False)

    page = browser.new_page()

    page.goto("https://www.bbbink.com/")

    num_categories = 500
    categories = []
    bimbambonussen = []

    try:
        continue_button = page.locator("#closeVersionBtn")
        continue_button.click()
        print("Clicked continue button")

        start_button = page.locator("#startButton")
        start_button.click()
        print("Clicked start button")

        loading = ""
        for j in range(num_categories):

            try:
                bimbambonus = page.locator("#powerUpDisplay")
                if bimbambonus.inner_text() not in bimbambonussen and bimbambonus.inner_text() != "":
                    bimbambonussen.append(bimbambonus.inner_text())
                    #print(f"bimbambonus {len(bimbambonussen)} gestolen: {bimbambonus.inner_text()}")

            except Exception as e:
                pass
                
            category_button = page.locator("#questionDisplay")
            # print(f"Found category button {j}, text: {category_button.inner_text()}")

            if category_button.inner_text() not in categories:
                categories.append(category_button.inner_text())
                print(f"categorie {len(categories)} gestolen: {category_button.inner_text()}")

            volgende_vraag = page.locator("#startButton")
            volgende_vraag.click()

    except Exception as e:
        print(f"Error {e}")
        browser.close()

    print(f"Scraping Finished! Stole {len(categories)} categories:")
    for i in range(len(categories)):
        print(f"Category {i}: {categories[i]}")

    print(f"{len(bimbambonussen)} bimbambonussen genakt!:")
    for i in range(len(bimbambonussen)):
        print(f"Bimbambonus {i}: {bimbambonussen[i]}")

    browser.close()

    with open("categories.json", "w", encoding="utf-8") as f:
        json.dump(categories, f, ensure_ascii=False, indent=2)
    
    with open("bimbambonussen.json", "w", encoding="utf-8") as f:
        json.dump(bimbambonussen, f, ensure_ascii=False, indent=2)

    
        
