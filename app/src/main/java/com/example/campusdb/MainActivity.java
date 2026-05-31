package com.example.campusdb;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.campusdb.model.Student;
import com.example.campusdb.service.StudentService;

public class MainActivity extends AppCompatActivity {

    private EditText etLastName;
    private EditText etFirstName;
    private EditText etStudentId;
    private TextView tvResult;

    private StudentService studentService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        studentService = new StudentService(this);

        etLastName = findViewById(R.id.et_last_name);
        etFirstName = findViewById(R.id.et_first_name);
        etStudentId = findViewById(R.id.et_student_id);
        tvResult = findViewById(R.id.tv_result);

        Button btnAdd = findViewById(R.id.btn_add);
        Button btnSearch = findViewById(R.id.btn_search);
        Button btnDelete = findViewById(R.id.btn_delete);

        btnAdd.setOnClickListener(v -> addStudent());
        btnSearch.setOnClickListener(v -> searchStudent());
        btnDelete.setOnClickListener(v -> deleteStudent());
    }

    private void addStudent() {
        String lastName = etLastName.getText().toString().trim();
        String firstName = etFirstName.getText().toString().trim();

        if (lastName.isEmpty() || firstName.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        studentService.create(new Student(lastName, firstName));
        etLastName.setText("");
        etFirstName.setText("");

        Toast.makeText(this,
                getString(R.string.toast_added),
                Toast.LENGTH_SHORT).show();
    }

    private void searchStudent() {
        String idText = etStudentId.getText().toString().trim();

        if (idText.isEmpty()) {
            Toast.makeText(this,
                    getString(R.string.toast_enter_id),
                    Toast.LENGTH_SHORT).show();
            return;
        }

        Student s = studentService.findById(Integer.parseInt(idText));

        if (s == null) {
            tvResult.setText("");
            Toast.makeText(this,
                    getString(R.string.toast_not_found),
                    Toast.LENGTH_SHORT).show();
            return;
        }

        tvResult.setText("👤 " + s.getLastName()
                + " " + s.getFirstName()
                + " (ID: " + s.getId() + ")");
    }

    private void deleteStudent() {
        String idText = etStudentId.getText().toString().trim();

        if (idText.isEmpty()) {
            Toast.makeText(this,
                    getString(R.string.toast_enter_id),
                    Toast.LENGTH_SHORT).show();
            return;
        }

        Student s = studentService.findById(Integer.parseInt(idText));

        if (s == null) {
            Toast.makeText(this,
                    getString(R.string.toast_not_found),
                    Toast.LENGTH_SHORT).show();
            return;
        }

        studentService.delete(s);
        tvResult.setText("");
        etStudentId.setText("");

        Toast.makeText(this,
                getString(R.string.toast_deleted),
                Toast.LENGTH_SHORT).show();
    }
}