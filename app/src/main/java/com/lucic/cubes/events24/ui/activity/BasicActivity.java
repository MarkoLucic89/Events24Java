package com.lucic.cubes.events24.ui.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.lucic.cubes.events24.tools.LanguageTool;
import com.lucic.cubes.events24.ui.view.EventsMessage;

public class BasicActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LanguageTool.setLanguageResourceConfiguration(this);

    }
}
