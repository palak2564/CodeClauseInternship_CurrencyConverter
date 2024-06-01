# List of currencies and their values
money = {
    "dollars": 1.0,
    "euros": 0.85,
    "yen": 109.61,
    "pounds": 0.73,
    "aussie_dollars": 1.38,
    "canadian_dollars": 1.27,
    "swiss_francs": 0.92,
    "mexican_pesos": 20.05,
    "brazilian_reals": 5.17,
    "indian_rupees": 74.33,
    "south_african_rand": 14.52
}

# Function to print available currencies
def show_currencies():
    print("Here are the available currencies:")
    for currency in money:
        print(f"- {currency}")

# Function to convert money
def change_money(amount, from_money, to_money):
    # Check if the money types are valid
    if from_money not in money or to_money not in money:
        print("Oops! That's not a real money type.")
        return

    # Get the values for the money types
    from_value = money[from_money]
    to_value = money[to_money]

    # Check if the values are not zero
    if from_value == 0 or to_value == 0:
        print("Uh-oh! You can't convert with zero.")
        return

    # Convert the amount to dollars
    dollars = amount / from_value

    # Convert dollars to the new money type
    new_amount = dollars * to_value

    # Print the result
    print(f"{amount} {from_money} is {new_amount:.2f} {to_money}")

# Ask the user for input
print("Hi! I can help you change money!")
show_currencies()
amount = float(input("How much money do you have? "))
from_money = input("What kind of money is it? ").lower()
to_money = input("What kind of money do you want? ").lower()

# Convert the money
change_money(amount, from_money, to_money)
