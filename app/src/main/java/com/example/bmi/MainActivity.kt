package com.example.bmi

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*

import com.example.bmi.Modules.BMI
import com.example.bmi.ui.theme.BMITheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BMITheme {
                TopAppBarPanel()
                MainContent()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
//Top App Bar Panel
fun TopAppBarPanel() {
    TopAppBar(
        title = {
            Text(
                text = "BMI",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
        },
        navigationIcon = {
            Icon(
                painter = painterResource(R.drawable.human),
                contentDescription = "top panel icon",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        },
        colors = topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
    )
}

@Composable
fun MainContent() {
    val context = LocalContext.current

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            val bmi = BMI()

            var bmiIndex by remember { mutableStateOf("") }
            var bmiDescription by remember { mutableStateOf("") }
            var height by remember { mutableStateOf("") }
            var mass by remember { mutableStateOf("") }

            OutlinedTextField(
                value = height,
                label = { Text(text = "Enter height (sm)") },
                onValueChange = { if (it.all { char -> char.isDigit() }) height = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = mass,
                label = { Text(text = "Enter mass (kg)") },
                onValueChange = { if (it.all { char -> char.isDigit() || char == '.' } && it.count { char -> char == '.' } <= 1) mass = it else Toast.makeText(context, "Only 1 dot!", Toast.LENGTH_SHORT).show() },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )

            Button(
                onClick = {
                    val bmiResult = bmi.calculateBMI(mass.toDoubleOrNull() ?: 0.0, height.toDoubleOrNull() ?: 0.0)
                    bmiIndex = "BMI ≈ $bmiResult"
                    bmiDescription = bmi.checkBMI(bmiResult)
                },
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier.padding(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            )
            {
                Text(
                    text = "Calculate BMI",
                    fontSize = 28.sp
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = bmiIndex,
                textAlign = TextAlign.Center,
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = bmiDescription,
                textAlign = TextAlign.Center
            )
        }
    }
}