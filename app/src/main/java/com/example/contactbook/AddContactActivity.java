package com.example.contactbook;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddContactActivity extends AppCompatActivity {

    private EditText etName, etPhone, etEmail;
    private Spinner spCategory;
    private RadioGroup rgPhoneType;
    private Button btnAdd;
    private ContactsOpenHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_contact);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHelper = new ContactsOpenHelper(this);

        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etEmail = findViewById(R.id.etEmail);
        spCategory = findViewById(R.id.spCategory);
        rgPhoneType = findViewById(R.id.rgPhoneType);
        btnAdd = findViewById(R.id.btnAdd);

        String[] categories = {"Family", "Friend", "Work", "Other"};
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, categories);
        spCategory.setAdapter(categoryAdapter);

        btnAdd.setOnClickListener(v -> saveContact());
    }

    private void saveContact() {
        String name = etName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String category = spCategory.getSelectedItem().toString();

        if (name.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "Please enter name and phone", Toast.LENGTH_SHORT).show();
            return;
        }

        int selectedId = rgPhoneType.getCheckedRadioButtonId();
        String phoneType = "Mobile";
        if (selectedId != -1) {
            RadioButton selectedRadioButton = findViewById(selectedId);
            phoneType = selectedRadioButton.getText().toString();
        }

        dbHelper.addContact(name, phone, email, category, phoneType);

        Toast.makeText(this, "Contact added successfully", Toast.LENGTH_SHORT).show();
        finish();
    }
}