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
public class Trabajador_ActivityTest {

    @Rule
    public ActivityScenarioRule<Login_Activity> mActivityScenarioRule =
            new ActivityScenarioRule<>(Login_Activity.class);

    @Test
    public void trabajador_ActivityTest() {
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.usernameEditText),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("trabajador@gmail.com"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.passwordEditText),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("trabajador"), closeSoftKeyboard());

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
                allOf(withId(R.id.inscritos_trabajador), withContentDescription("Inscritos"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bottom_navigation_trabajador),
                                        0),
                                1),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());

        ViewInteraction bottomNavigationItemView2 = onView(
                allOf(withId(R.id.buscador_trabajador), withContentDescription("Buscar campamento"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bottom_navigation_trabajador),
                                        0),
                                0),
                        isDisplayed()));
        bottomNavigationItemView2.perform(click());

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.recycler_campamentos_trabajador),
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                1)));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.fab),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        floatingActionButton.perform(click());

        pressBack();

        ViewInteraction bottomNavigationItemView3 = onView(
                allOf(withId(R.id.inscritos_trabajador), withContentDescription("Inscritos"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bottom_navigation_trabajador),
                                        0),
                                1),
                        isDisplayed()));
        bottomNavigationItemView3.perform(click());

        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.recycler_campamentos_inscrito),
                        childAtPosition(
                                withClassName(is("android.widget.FrameLayout")),
                                0)));
        recyclerView2.perform(actionOnItemAtPosition(0, click()));

        pressBack();

        ViewInteraction bottomNavigationItemView4 = onView(
                allOf(withId(R.id.herramientas_trabajador), withContentDescription("Herramientas"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bottom_navigation_trabajador),
                                        0),
                                2),
                        isDisplayed()));
        bottomNavigationItemView4.perform(click());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.button_aceptados), withText("Campamentos aceptados"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_container_trabajador),
                                        0),
                                1),
                        isDisplayed()));
        materialButton2.perform(click());

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.button_lista_asistentes), withText("Lista asistentes"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_container_trabajador),
                                        0),
                                0),
                        isDisplayed()));
        materialButton3.perform(click());

        ViewInteraction recyclerView3 = onView(
                allOf(withId(R.id.recycler_campamentos_trabajador),
                        childAtPosition(
                                withClassName(is("android.widget.FrameLayout")),
                                0)));
        recyclerView3.perform(actionOnItemAtPosition(0, click()));

        pressBack();

        ViewInteraction materialButton4 = onView(
                allOf(withId(R.id.button_tareas), withText("Tareas"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_container_trabajador),
                                        0),
                                3),
                        isDisplayed()));
        materialButton4.perform(click());

        ViewInteraction appCompatImageButton = onView(
                allOf(withId(R.id.imgTick), withContentDescription("EasyCamp"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recycler_tareas_trabajador),
                                        0),
                                1),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction bottomNavigationItemView5 = onView(
                allOf(withId(R.id.perfil_trabajador), withContentDescription("Usuario"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bottom_navigation_trabajador),
                                        0),
                                3),
                        isDisplayed()));
        bottomNavigationItemView5.perform(click());

        ViewInteraction materialButton5 = onView(
                allOf(withId(R.id.btnPerfil), withText("Perfil"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_container_trabajador),
                                        0),
                                0),
                        isDisplayed()));
        materialButton5.perform(click());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.etNombre), withText("Laura"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_container_trabajador),
                                        0),
                                5),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("Lauro"));

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.etNombre), withText("Lauro"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_container_trabajador),
                                        0),
                                5),
                        isDisplayed()));
        appCompatEditText4.perform(closeSoftKeyboard());

        ViewInteraction materialButton6 = onView(
                allOf(withId(R.id.btnActualizar), withText("Actualizar"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_container_trabajador),
                                        0),
                                10),
                        isDisplayed()));
        materialButton6.perform(click());

        ViewInteraction bottomNavigationItemView6 = onView(
                allOf(withId(R.id.perfil_trabajador), withContentDescription("Usuario"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bottom_navigation_trabajador),
                                        0),
                                3),
                        isDisplayed()));
        bottomNavigationItemView6.perform(click());

        ViewInteraction materialButton7 = onView(
                allOf(withId(R.id.btnCerrarSesion), withText("Cerrar Sesi�n"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_container_trabajador),
                                        0),
                                1),
                        isDisplayed()));
        materialButton7.perform(click());
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
