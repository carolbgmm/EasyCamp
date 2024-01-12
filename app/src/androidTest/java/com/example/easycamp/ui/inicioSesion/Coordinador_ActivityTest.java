package com.example.easycamp.ui.inicioSesion;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.easycamp.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class Coordinador_ActivityTest {

    @Rule
    public ActivityScenarioRule<Login_Activity> mActivityScenarioRule =
            new ActivityScenarioRule<>(Login_Activity.class);

    @Test
    public void coordinador_ActivityTest() {
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.usernameEditText),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("coor@gmail.com"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.passwordEditText),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("coordinador"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.loginButton), withText("Iniciar sesi�n"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.perfil_coordinador), withContentDescription("Perfil"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bottom_navigation_coordinador),
                                        0),
                                1),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());

        ViewInteraction bottomNavigationItemView2 = onView(
                allOf(withId(R.id.crear_campamento), withContentDescription("Campamento"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bottom_navigation_coordinador),
                                        0),
                                0),
                        isDisplayed()));
        bottomNavigationItemView2.perform(click());

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.recyclerViewCampamentos),
                        childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                0)));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction bottomNavigationItemView3 = onView(
                allOf(withId(R.id.navigation_descripcion), withContentDescription("Lista asistentes"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bottom_navigation_detalle),
                                        0),
                                1),
                        isDisplayed()));
        bottomNavigationItemView3.perform(click());

        ViewInteraction bottomNavigationItemView4 = onView(
                allOf(withId(R.id.navigation_resumen), withContentDescription("Trabajadores"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bottom_navigation_detalle),
                                        0),
                                0),
                        isDisplayed()));
        bottomNavigationItemView4.perform(click());

        pressBack();

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.btnCrearCampamento),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_container_coordinador),
                                        0),
                                1),
                        isDisplayed()));
        materialButton2.perform(click());

        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.recyclerViewCampamentos),
                        childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                0)));
        recyclerView2.perform(actionOnItemAtPosition(5, click()));

        pressBack();

        ViewInteraction recyclerView3 = onView(
                allOf(withId(R.id.recyclerViewCampamentos),
                        childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                0)));
        recyclerView3.perform(actionOnItemAtPosition(1, click()));

        pressBack();

        ViewInteraction bottomNavigationItemView5 = onView(
                allOf(withId(R.id.perfil_coordinador), withContentDescription("Perfil"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bottom_navigation_coordinador),
                                        0),
                                1),
                        isDisplayed()));
        bottomNavigationItemView5.perform(click());

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.btnCerrarSesion), withText("Cerrar Sesi�n"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_container_coordinador),
                                        0),
                                1),
                        isDisplayed()));
        materialButton3.perform(click());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
