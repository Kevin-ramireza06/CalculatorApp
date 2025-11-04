package com.pmm.a22;

import static com.pmm.a22.R.*;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ContentInfoCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.pmm.a22.calculator.Calculator;

public class MainActivity extends AppCompatActivity {

    private final Calculator _calculator= new Calculator();

    TextView pantalla;
    TextView pantallaError;
    Switch switchColor;

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
        setContentView(R.layout.activity_calculator);

        pantalla = findViewById(R.id.pantalla);
        switchColor = findViewById(id.Estilos);

        if (savedInstanceState != null) {
            resultado = savedInstanceState.getString("resultado");
            calculado = savedInstanceState.getBoolean("calculado", false);

            pantalla.setText(savedInstanceState.getString("pantalla"));

            _calculator.setOperand(savedInstanceState.getString("firstOperand"));

            _calculator.setNewOperation(savedInstanceState.getBoolean("newOperation", false));

            _calculator.setOperand(savedInstanceState.getString("secondOperand"));
            _calculator.setOperator((Calculator.Operators) savedInstanceState.getSerializable("operator"));
        }

        switchColor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull CompoundButton buttonView, boolean isChecked) {
                View main = findViewById(id.main);
                if (isChecked) {
                    main.setBackgroundColor(Color.DKGRAY);
                } else {
                    main.setBackgroundColor(Color.GRAY);
                }
            }
        });

        pantalla = findViewById(id.pantalla);
        pantallaError = findViewById(id.pantallaError);
        switchColor = findViewById(id.Estilos);
        /*Button boton0 = findViewById(id.boton0);
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
        Button botonMas = findViewById(id.botonMas);*/
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("resultado", resultado);
        outState.putBoolean("calculado", calculado);

        outState.putString("pantalla", pantalla.getText().toString());

        outState.putString("firstOperand", _calculator.getFirstOperand());
        outState.putString("secondOperand", _calculator.getSecondOperand());
        outState.putSerializable("operator", _calculator.getOperator());
        outState.putBoolean("newOperation", _calculator.isNewOperation());
    }
    public void operandClick(View view) {

        pantalla = findViewById(R.id.pantalla);
        pantallaError = findViewById(id.pantallaError);
        pantallaError.setText("");

        if (calculado) clearClick(null);

        int operandButtonId= view.getId();
        System.out.println(operandButtonId);

        String numero=((Button)view).getText().toString();

        resultado+= numero;

        _calculator.setOperand(numero);

        pantalla.setText(resultado);
    }

    public void operatorClick(View view) {

        pantalla = findViewById(R.id.pantalla);
        pantallaError = findViewById(id.pantallaError);
        pantallaError.setText("");

        Calculator.Operators operator= null;
        String operador=((Button)view).getText().toString();

        if (!_calculator.isNewOperation()) {
            double res = _calculator.calculate();
            resultado = String.valueOf(res);
            pantalla.setText(resultado);

            _calculator.clear();
            _calculator.setOperand(resultado);
        }

        _calculator.setOperator(operator);
        resultado+= operador;

        switch(operador){
            case "+":
                _calculator.setOperator(Calculator.Operators.ADD);
                break;
            case "-":
                _calculator.setOperator(Calculator.Operators.SUBSTRACT);
                break;
            case "*":
                _calculator.setOperator(Calculator.Operators.MULTIPLY);
                break;
            case "/":
                _calculator.setOperator(Calculator.Operators.DIVIDE);
                break;
        }

        pantalla.setText(resultado);
    }

    public void clearClick(View view) {

        pantalla = findViewById(R.id.pantalla);

        _calculator.clear();
        calculado= false;
        resultado= "";

        pantalla.setText("");
    }

    public void igualClick(View view) {
        pantalla = findViewById(R.id.pantalla);
        pantallaError = findViewById(id.pantallaError);

        if (resultado.isEmpty()){
            pantallaError.setText("La calculadora estra vacia");
        } else {
            try {
                double res = _calculator.calculate();
                pantalla.setText(String.valueOf(res));
                resultado = String.valueOf(res);
                calculado = true;
                _calculator.clear();
                _calculator.setOperand(resultado); // para usar el resultado como primer operando si sigues
            } catch (Calculator.DivisionByZeroException e) {
                pantalla.setText("0.0");
                pantallaError.setText("ERROR: No se puede dividir entre 0");
                resultado = "";
                _calculator.clear();
            } catch (Calculator.MissingOperandException e){
                pantallaError.setText("Falta un Operando");
            }
        }

    }
}