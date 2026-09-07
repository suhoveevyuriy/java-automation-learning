package webshop.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class HomePage {

    private final Page page;

    public HomePage(Page page) {
        this.page = page;
    }

    public HomePage open() {
        page.navigate("/");
        return this;
    }

    public LoginPage openLoginPage() {
        loginLink().click();
        return new LoginPage(page);
    }

    public RegisterPage openRegisterPage() {
        page.locator(".header-links .ico-register").click();
        return new RegisterPage(page);
    }

    public void logout() {
        logoutLink().click();
    }

    public SearchResultsPage search(String query) {
        page.locator("#small-searchterms").fill(query);
        page.locator("#small-searchterms").press("Enter");

        return new SearchResultsPage(page);
    }

    public Locator accountLink() {
        return page.locator(".header-links .account");
    }

    public Locator loginLink() {
        return page.locator(".header-links .ico-login");
    }

    public Locator logoutLink() {
        return page.locator(".header-links .ico-logout");
    }
}