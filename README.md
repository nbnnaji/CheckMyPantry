# 🧺 Pantry Inventory App 💰🔍

A smart and modern Android app that helps you **match two products that add up to a target price** — great for budgeting, shopping, or using up leftovers!

Built with:
- 🧱 **Jetpack Compose**

---

## 🌟 Key Features

### 🧠 Smart Price Matcher  
Enter a **target amount** (e.g., `$50`) and select **two pantry items** that add up to the target value. Great for:

- 🛒 Budget shopping

### 💡 Clean Modern UI  
Crafted with **Jetpack Compose** for smooth performance and a delightful user experience.

---

## 🖼 Preview

### 🎯 Target Price Matching Demo

> *Find 2 items that match a given total price (e.g., `$30`)*  
> _(powered by a simple `Two Sum` algorithm)_

```
Target Price: $30  
Matches:  
🧃 Juice - $10  
🥫 Pasta Sauce - $20  
```

---

## 🔧 Tech Stack

| Category        | Technology                     |
|----------------|---------------------------------|
| Language        | Kotlin                         |
| UI Framework    | Jetpack Compose                |
| Build Tool      | Gradle                         |

---

## 🚀 How It Works

### 🎯 Use Price Matcher
- Input a target price
- The app lets you know if the **2 items** add up to your budget
- Uses optimized Two Sum logic

### 🧠 Sample Code (Matching Logic)

```kotlin
fun findMatchingPair(products: List<Product>, target: Int): Pair<Product, Product>? {
    val map = mutableMapOf<Int, Product>()
    for (product in products) {
        val complement = target - product.price
        map[complement]?.let {
            return it to product
        }
        map[product.price] = product
    }
    return null
}
```

---

## 📱 Screenshots
<img width="361" height="710" alt="Screenshot 2025-07-30 at 11 24 43 PM" src="https://github.com/user-attachments/assets/b25e1a00-99d5-426e-a3a4-e88e22c76abe" />


---

## 🛠 Setup Instructions

1. Clone the repo:
   ```bash
   git clone https://github.com/your-username/pantry-inventory-match.git
   ```

2. Open in Android Studio (Electric Eel or newer)

3. Run on a **real device** (for camera support)

---

## 🔮 Coming Soon

- [ ] Cloud sync via Firebase ☁️  
- [ ] Recipe suggestions based on matched ingredients 🧑‍🍳  
- [ ] Dark mode support 🌙  
- [ ] Notification reminders for low stock or expiry ⏰  

---

## 🤝 Contributing

Pull requests are welcome! Feel free to open issues or request features.

---

## 📜 License

MIT License

---

### ✨ Stay smart in the kitchen, and even smarter with your spending!
