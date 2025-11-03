package com.pmm.a22;

import static com.pmm.a22.R.*;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.pmm.a22.calculator.Calculator;

public class MainActivity extends AppCompatActivity {

    private final Calculator _calculator= new Calculator();

    private String resultado= "";
    private boolean calculado= false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_calculator);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView pantalla = findViewById(id.pantalla);
        Button boton0 = findViewById(id.boton0);
        Button boton1 = findViewById(id.boton1);
        Button boton2 = findViewById(id.boton2);
        Button boton3 = findViewById(id.boton3);
        Button boton4 = findViewById(id.boton4);
        Button boton5 = findViewById(id.boton5);
        Button boton6 = findViewById(id.boton6);
        Button boton7 = findViewById(id.boton7);
        Button boton8 = findViewById(id.boton8);
        Button boton9 = findViewById(id.boton9);
        Button botonDivicion = findViewById(id.botonDivicion);
        Button botonPor = findViewById(id.botonPor);
        Button botonMenos = findViewById(id.botonMenos);
        Button botonMas = findViewById(id.botonMas);
    }


    public void operandClick(View view) {

        TextView pantalla = findViewById(R.id.pantalla);

        if (calculado) clearClick(null);

        int operandButtonId= view.getId();
        System.out.println(operandButtonId);

        String numero=((Button)view).getText().toString();

        // COMPLETAR COMO PARTE DEL EJERCICIO SELECCIONANDO EL OPERANDO

        resultado+= numero;

        _calculator.setOperand(numero);

        // COMPLETAR COMO PARTE DEL EJERCICIO ACTUALIZANDO LA PANTALLA DE LA CALCULADORA

        pantalla.setText(resultado);
    }

    public void operatorClick(View view) {

        TextView pantalla = findViewById(R.id.pantalla);

        int operatorButtonId= view.getId();
        System.out.println(operatorButtonId);
        Calculator.Operators operator= null;
        String operador=((Button)view).getText().toString();

        if (!_calculator.isNewOperation()) {

            // COMPLETAR COMO PARTE DEL EJERCICIO EJECUTANDO EL CÁLCULO

            return;
        }

        // COMPLETAR COMO PARTE DEL EJERCICIO SELECCIONANDO EL OPERADOR

        _calculator.setOperator(operator);
        resultado+= operador;

        // COMPLETAR COMO PARTE DEL EJERCICIO ACTUALIZANDO LA PANTALLA DE LA CALCULADORA

        pantalla.setText(resultado);

        switch(operador){
            case "+": _calculator.setOperator(Calculator.Operators.ADD); break;
            case "-": _calculator.setOperator(Calculator.Operators.SUBSTRACT); break;
            case "*": _calculator.setOperator(Calculator.Operators.MULTIPLY); break;
            case "/": _calculator.setOperator(Calculator.Operators.DIVIDE); break;
        }
    }

    public void clearClick(View view) {

        TextView pantalla = findViewById(R.id.pantalla);

        _calculator.clear();
        calculado= false;
        resultado= "";

        // COMPLETAR COMO PARTE DEL EJERCICIO ACTUALIZANDO LA PANTALLA DE LA CALCULADORA

        pantalla.setText("");
    }

}