package com.example.myproyect;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.myproyect.actividades.actividades.Login_Activity;
import com.example.myproyect.actividades.actividades.RegistroActivity;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)

public class Login_ActivityTest {

    @Test
    public void testLoginActivity() {

        ActivityScenario<Login_Activity> scenario = ActivityScenario.launch(Login_Activity.class);
        // Aquí puedes interactuar con la actividad y realizar las pruebas necesarias
    }
    @Test
    public void testRegistroActivity() {

        ActivityScenario<RegistroActivity> scenario = ActivityScenario.launch(RegistroActivity.class);
        // Aquí puedes interactuar con la actividad y realizar las pruebas necesarias
    }

}
