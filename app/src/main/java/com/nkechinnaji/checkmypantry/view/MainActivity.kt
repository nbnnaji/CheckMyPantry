package com.nkechinnaji.checkmypantry.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nkechinnaji.checkmypantry.model.Product
import com.nkechinnaji.checkmypantry.ui.theme.CheckMyPantryTheme
import kotlinx.coroutines.launch
import kotlin.random.Random


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CheckMyPantryTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ProductSelectionApp()
                }
            }
        }
    }

    fun generateRandomProducts(): List<Product> {
        val foodItems = listOf(
            "Apples",
            "Bananas",
            "Carrots",
            "Tomatoes",
            "Milk",
            "Cheese",
            "Eggs",
            "Bread",
            "Rice",
            "Pasta",
            "Chicken",
            "Beef",
            "Yogurt",
            "Butter",
            "Spinach",
            "Potatoes",
            "Orange Juice",
            "Cereal",
            "Granola Bars",
            "Peanut Butter"
        )
        return foodItems.shuffled().take(10).map { name ->
            Product(name, Random.nextInt(5, 30)) // Smaller prices for grocery items
        }
    }

    @Composable
    fun ProductSelectionApp() {
        var products by remember { mutableStateOf(generateRandomProducts()) }
        var targetText by remember { mutableStateOf(TextFieldValue("50")) }
        val target = targetText.text.toIntOrNull() ?: 0

        var selected by remember { mutableStateOf<List<Product>>(emptyList()) }

        // Clear selection when target changes
        LaunchedEffect(target) {
            selected = emptyList()
        }

        ProductSelectionScreen(
            products = products,
            target = target,
            targetText = targetText,
            selected = selected,
            onSelectedChange = { selected = it },
            onTargetChange = { targetText = it },
            onRegenerate = {
                products = generateRandomProducts()
                selected = emptyList()
            }
        )
    }

    // --- UI Composable ---
    @Composable
    fun ProductSelectionScreen(
        products: List<Product>,
        target: Int,
        targetText: TextFieldValue,
        selected: List<Product>,
        onSelectedChange: (List<Product>) -> Unit,
        onTargetChange: (TextFieldValue) -> Unit,
        onRegenerate: () -> Unit
    ) {
        val snackbarHostState = remember { SnackbarHostState() }
        val scope = rememberCoroutineScope()



        // ✅ Show snackbar when 2 items selected
        LaunchedEffect(selected, target) {
            if (selected.size == 2) {
                val p1 = selected.getOrNull(0)
                val p2 = selected.getOrNull(1)
                val match = isMatching(p1, p2, target)

                scope.launch {
                    snackbarHostState.showSnackbar(
                        if (match)
                            "✅ ${p1?.name} + ${p2?.name} = $$target"
                        else
                            "❌ ${p1?.name} + ${p2?.name} ≠ $$target"
                    )
                }
            }
        }

        Box(
            modifier = Modifier.fillMaxSize()
                .padding(WindowInsets.systemBars.asPaddingValues())
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()

            ) {
                OutlinedTextField(
                    value = targetText,
                    onValueChange = onTargetChange,
                    label = { Text("Target Sum") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = { onSelectedChange(emptyList()) },
                        enabled = selected.isNotEmpty(),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onTertiary)
                    ) {
                        Text(
                            "Clear Selection", color = MaterialTheme.colorScheme.surfaceVariant,
                        )
                    }

                    Button(
                        onClick = onRegenerate,
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                    ) {
                        Text("New List", color = MaterialTheme.colorScheme.onSecondary)

                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Selected ${selected.size} / 2",
                    modifier = Modifier.padding(vertical = 4.dp),
                    fontWeight = FontWeight.Medium
                )

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(
                        start = 8.dp, end = 8.dp, top = 8.dp, bottom = 100.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    items(products) { product ->
                        val isSelected = product in selected

                        val animatedColor by animateColorAsState(
                            targetValue = if (isSelected)
                                MaterialTheme.colorScheme.tertiary.copy(alpha = 0.2f)
                            else
                                MaterialTheme.colorScheme.surface.copy(alpha = 0.2f)
                        )

                        val animatedElevation by animateDpAsState(
                            targetValue = if (isSelected) 12.dp else 6.dp,
                            label = "CardElevation"
                        )

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(130.dp)
                                .clip(MaterialTheme.shapes.large)
                                .clickable {
                                    when {
                                        isSelected -> onSelectedChange(selected - product)
                                        selected.size < 2 -> onSelectedChange(selected + product)
                                        else -> {
                                            scope.launch {
                                                snackbarHostState.showSnackbar("Only 2 products can be selected.")
                                            }
                                        }
                                    }
                                },
                            shape = RoundedCornerShape(4.dp),
                           colors = CardDefaults.cardColors(containerColor = animatedColor)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 16.dp), // Adjusted padding
                                horizontalAlignment = Alignment.CenterHorizontally // Center horizontally
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ShoppingCart,
                                    contentDescription = null,
                                    tint = Color(0xFF2E8758),
                                    modifier = Modifier.size(28.dp)
                                )
                                Spacer(modifier = Modifier.weight(0.5f)) // Pushes content below to the bottom
                                Text(
                                    product.name,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.align(Alignment.CenterHorizontally) // Align text to start
                                )
                                Text(
                                    "$${product.price}",
                                    fontSize = 16.sp,
                                    color = Color.Gray,
                                    modifier = Modifier.align(Alignment.CenterHorizontally) // Align text to start
                                    )
                            }
                        }
                    }
                }
            }
            val match = isMatching(selected.getOrNull(0), selected.getOrNull(1), target)

            val backgroundColor = if (match) MaterialTheme.colorScheme.surface else Color(0xFFF3E5F5)
            val textColor = if (match) MaterialTheme.colorScheme.onSurface else Color(0xFF6A1B9A)

            // ✅ Snackbar host
            SnackbarHost(
                hostState = snackbarHostState,
                snackbar = { data ->
                    Snackbar(
                        containerColor = Color(0xFF27B4F5),
                        contentColor = Color(0xFF000000),
                        snackbarData = data
                    )
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(WindowInsets.navigationBars.asPaddingValues())

            )
        }
    }


    fun isMatching(p1: Product?, p2: Product?, target: Int): Boolean {
        return if (p1 != null && p2 != null) (p1.price + p2.price == target) else false
    }


    @Composable
    fun SuggestedPairScreen(products: List<Product>, target: Int) {
        val pair = remember { findProductPair(products, target) }

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Gift Card Amount: $$target", fontSize = 20.sp)

            if (pair != null) {
                Text("You can buy:", fontSize = 18.sp)
                Text("- ${pair.first.name} ($${pair.first.price})")
                Text("- ${pair.second.name} ($${pair.second.price})")
            } else {
                Text("No matching pair found.", fontSize = 18.sp)
            }
        }
    }


    fun findProductPair(products: List<Product>, target: Int): Pair<Product, Product>? {
        val map = mutableMapOf<Int, Product>()
        for (product in products) {
            val needed = target - product.price
            if (map.containsKey(needed)) {
                return Pair(map[needed]!!, product)
            }
            map[product.price] = product
        }
        return null
    }

    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
    }

    @Preview(showBackground = true)
    @Composable
    fun SuggestedPairScreenPreview() {
        CheckMyPantryTheme {
            SuggestedPairScreen(
                products = listOf(
                    Product("Headphones", 30),
                    Product("Charger", 20),
                    Product("Phone Case", 25),
                    Product("USB Cable", 10),
                    Product("Power Bank", 40)
                ),
                target = 50
            )
        }
    }
}