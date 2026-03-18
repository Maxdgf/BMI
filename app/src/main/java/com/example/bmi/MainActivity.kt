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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*

import com.example.bmi.utils.BMI
import com.example.bmi.ui.theme.BMITheme
import com.example.bmi.utils.BMIData

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BMITheme {
                MainContent()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent() {
    val context = LocalContext.current

    val bmi = remember { BMI() }

    var bmiResult by rememberSaveable {
        mutableStateOf<Pair<BMIData?, String?>>(Pair(null, null))
    }
    var height by rememberSaveable { mutableStateOf("") }
    var mass by rememberSaveable { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.app_name),
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
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center,
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                OutlinedTextField(
                    value = height,
                    label = { Text(text = "Enter height (sm)") },
                    onValueChange = {
                        if (it.all { char -> char.isDigit() || char == '.' } && it.count { char -> char == '.' } <= 1)
                            height = it
                        else
                            Toast.makeText(context, "Only 1 dot!", Toast.LENGTH_SHORT).show()
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    value = mass,
                    label = { Text(text = "Enter mass (kg)") },
                    onValueChange = {
                        if (it.all { char -> char.isDigit() || char == '.' } && it.count { char -> char == '.' } <= 1)
                            mass = it
                        else
                            Toast.makeText(context, "Only 1 dot!", Toast.LENGTH_SHORT).show()
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )

                Button(
                    onClick = {
                        val massData = mass.toDoubleOrNull()
                        val heightData = height.toDoubleOrNull()

                        if (massData != null && heightData != null)
                            bmiResult = bmi.calculateBMI(massData, heightData)
                        else
                            Toast.makeText(context, "Little data!", Toast.LENGTH_SHORT).show()
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

                // bmi data view
                bmiResult.first?.let { bmi ->
                    Spacer(modifier = Modifier.height(30.dp))

                    Text(
                        text = "BMI ≈ ${bmi.index}",
                        textAlign = TextAlign.Center,
                        fontSize = 35.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(30.dp))

                    Text(
                        text = bmi.description,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // dangers view
                    bmi.dangers?.let {
                        Text(text = "Possible dangers:\n${it.joinToString("\n") { "● $it" }}")
                    }
                } ?: bmiResult.second?.let { exception ->  Text(text = "Error: $exception")} // show exception
            }
        }
    }
}