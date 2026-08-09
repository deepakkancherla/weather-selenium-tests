package com.weatherlab.tests;

import com.weatherlab.data.TestUser;
import com.weatherlab.pages.DashboardPage;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("favorites")
class FavoriteTests extends BaseUiTest {
    @Test
    @Tag("smoke")
    void fav001_userCanSaveCurrentCity() {
        TestUser user = createApiUser("FAV-001");
        DashboardPage dashboard = logIn(user);

        dashboard.toggleFavorite();
        dashboard.waitForFavoriteButton("Saved");

        assertEquals(1, dashboard.favoriteCards().size());
    }

    @Test
    void fav003_savedCityPersistsAfterSigningBackIn() {
        TestUser user = createApiUser("FAV-003");
        DashboardPage dashboard = logIn(user);
        dashboard.toggleFavorite();
        dashboard.waitForFavoriteButton("Saved");
        dashboard.logout().login(user.email(), user.password());

        DashboardPage restoredDashboard = new DashboardPage(driver).waitUntilLoaded();
        restoredDashboard.waitForFavoriteButton("Saved");

        assertEquals(1, restoredDashboard.favoriteCards().size());
    }

    @Test
    void fav004_userCanRemoveSavedCity() {
        TestUser user = createApiUser("FAV-004");
        DashboardPage dashboard = logIn(user);
        dashboard.toggleFavorite();
        dashboard.waitForFavoriteButton("Saved");

        dashboard.removeFavorite("Austin");

        assertTrue(dashboard.emptyFavoritesIsVisible());
    }

    @Test
    void fav006_newAccountStartsWithHelpfulEmptyState() {
        TestUser user = createApiUser("FAV-006");

        assertTrue(logIn(user).emptyFavoritesIsVisible());
    }
}

