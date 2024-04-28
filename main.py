import json
from llamaapi import LlamaAPI
from openai import OpenAI
import regex as re

# Initialize the SDK
llama = LlamaAPI("LL-Z1MNZGxiDuLn5B0jNrIpf9NTQo2kEJf5V9mVqE6Nkdqi4dY6aE6ZMN2MLVMV7gjk")

client = OpenAI(api_key = "LL-Z1MNZGxiDuLn5B0jNrIpf9NTQo2kEJf5V9mVqE6Nkdqi4dY6aE6ZMN2MLVMV7gjk", base_url = "https://api.llama-api.com")

def get_spending_tip():
    response = client.chat.completions.create(
        model="llama-13b-chat",
        messages=[
            {"role": "system", "content": "You are a proffesional financial advisor. Your answers should be serious and proffesional."},
            {"role": "user", "content": "Give me a very short original financial tip for monthly spending up to 10 words"}
        ],
        temperature=0.8
    )
    return remove_emojis(response.choices[0].message.content.split(".")[0].replace("\"", "") + ".")

def get_category_tip():
    with open("data.json") as file:
        data = json.load(file)
    with open("goals.json") as file:
        goals = json.load(file)
    response = client.chat.completions.create(
        model="llama-13b-chat",
        messages=[
                {"role": "system", "content":
                    "You can reply with one short sentence. You are a finance advisor providing tips for better managing income based on the data user gives."},
                {"role": "user", "content": "Give me a very short tip, up to 10 words based on my data. Remove all the unnecessary words in your response. My goals are: " + json.dumps(goals) + ". My data is: " + json.dumps(data)}
        ],
        temperature=1
    )
    return remove_emojis(response.choices[0].message.content)

def get_category(word):
    response = client.chat.completions.create(
        model="llama-13b-chat",
        messages=[
                {"role": "system", "content":
                    """
                    Categorize the users transaction into a category. Reply with only the category. Consither the following info:
                    GROCERIES when you see: 'food', 'store', 'groceries', 'fruit', 'vegetables', 'meat', 'milk', 'dairy', 'bread', 'pasta', 'rice', 'cereal', 'snacks', 'beverages', 'produce', 'dairy & eggs', 'bakery', 'deli', 'frozen foods', 'canned goods', 'pantry staples', 'shopping list', 'shopping cart', 'grocery store', 'supermarket', 'aisle', 'checkout', 'sales', 'discounts', 'coupons', 'specials', 'organic', 'gluten-free', 'vegan', 'local', 'recipes', 'ingredients', 'meal prep', 'weekly meal plan', 'menu', 'cooking', 'baking', 'grilling', 'slow cooker', 'instant pot', 'nutrition', 'healthy eating', 'diet plan'.
                    BILLS when you see: 'electricity','water bill', 'bill', 'utilities', 'fees', 'fine', 'membership', 'dues', 'phone', 'internet', 'rent'.
                    TRAVEL when you see: 'flight', 'train', 'bus', 'car rental', 'hotel', 'accommodation', 'booking', 'reservation', 'ticket', 'travel agency', 'tour', 'vacation', 'holiday', 'destination', 'trip', 'journey', 'airport', 'seaport', 'cruise', 'cruise line', 'cabin', 'suite', 'tourist attraction', 'sightseeing', 'itinerary', 'passport', 'visa', 'insurance', 'luggage', 'baggage', 'packing', 'packing list', 'carry-on', 'checked baggage', 'boarding pass', 'departure', 'arrival', 'transfer', 'shuttle', 'taxi', 'rental car', 'rental service'.
                    TRANSPORT when you see: 'transportation', 'transport', 'vehicle', 'car', 'bus', 'train', 'subway', 'metro', 'tram', 'bike', 'bicycle', 'motorcycle', 'scooter', 'taxi', 'cab', 'ride-hailing', 'Uber', 'Lyft', 'public transit', 'commute', 'commuter', 'commuting', 'driver', 'passenger', 'fare', 'ticket', 'route', 'schedule', 'map', 'stop', 'station', 'terminal', 'platform', 'lane', 'lane closure', 'traffic', 'congestion', 'accident', 'detour', 'roadwork', 'construction', 'parking', 'parking spot', 'parking garage', 'parking lot', 'parking meter', 'toll', 'toll booth', 'highway', 'freeway', 'expressway', 'motorway', 'bridge', 'tunnel', 'ferry', 'cruise', 'flight', 'airport', 'airline', 'boarding', 'departure', 'arrival', 'baggage', 'baggage claim', 'security', 'customs', 'immigration', 'passport control', 'boarding pass', 'gate', 'jet bridge', 'runway', 'taxiway', 'air traffic control', 'aircraft', 'airplane', 'aircraft type', 'seating', 'aisle', 'window seat', 'middle seat', 'exit row', 'overhead bin', 'seatbelt', 'in-flight', 'meal service', 'entertainment', 'WiFi', 'flight attendant', 'captain', 'pilot', 'co-pilot', 'cabin crew', 'airline policy', 'lost luggage', 'delay', 'cancellation', 'diversion', 'connecting flight', 'layover', 'transfer', 'shuttle', 'rental car', 'rental service', 'car rental', 'gas', 'fuel', 'electric vehicle', 'EV charging', 'charging station', 'charging port', 'maintenance', 'repair', 'roadside assistance'.
                    FUN when you see: 'fun', 'entertainment', 'activities', 'recreation', 'leisure', 'play', 'amusement', 'pastime', 'enjoyment', 'hobby', 'game', 'playtime', 'fun time', 'outing', 'event', 'outing', 'adventure', 'excursion', 'explore', 'expedition', 'coffee', 'friends', 'party'.
                    You can reply with one of the following words: GROCERIES, BILLS, TRAVEL, TRANSPORT, FUN, OTHER"""},
                {"role": "user", "content": "put " + word + " in one of the categories (GROCERIES, BILLS, TRAVEL, TRANSPORT, FUN, OTHER). Reply with just the category"}
        ],
        temperature=0.2
    )
    return remove_emojis(response.choices[0].message.content)


def remove_emojis(text):
    emoji_pattern = re.compile(
        "[\U0001F600-\U0001F64F]|"  # emoticons
        "[\U0001F300-\U0001F5FF]|"  # symbols & pictographs
        "[\U0001F680-\U0001F6FF]|"  # transport & map symbols
        "[\U0001F700-\U0001F77F]|"  # alchemical symbols
        "[\U0001F780-\U0001F7FF]|"  # Geometric Shapes Extended
        "[\U0001F800-\U0001F8FF]|"  # Supplemental Arrows-C
        "[\U0001F900-\U0001F9FF]|"  # Supplemental Symbols and Pictographs
        "[\U0001FA00-\U0001FA6F]|"  # Chess Symbols
        "[\U0001FA70-\U0001FAFF]",  # Symbols and Pictographs Extended-A
        flags=re.UNICODE
    )
    cleaned_text = emoji_pattern.sub('', text)
    return cleaned_text


def chat(request):
    with open("data.json") as file:
        data = json.load(file)
    with open("goals.json") as file:
        goals = json.load(file)
    response = client.chat.completions.create(
        model="llama-13b-chat",
        messages=[
                {"role": "system", "content":
                    "You are a financial asistant. You always give an intellingent answer to all of the requests yzour client gives. You take into the consideration the users data."},
                {"role": "user", "content": request + "My data is " + json.dumps(data) + ". My goals are: " + json.dumps(goals)}
        ],
        temperature=0.7
    )
    return remove_emojis(response.choices[0].message.content)

def get_all():
    with open("data.json") as file:
        data = json.load(file)
        return data

def add(json_string):
    try:
        # Load the existing data from data.json
        with open('data.json', 'r') as file:
            data = json.load(file)
    except FileNotFoundError:
        # If the file doesn't exist, create an empty list
        data = []

    # Parse the JSON string to a Python dictionary
    new_transaction = json.loads(json_string)

    # Add the new transaction to the existing data
    data.append(new_transaction)

    # Write the updated data back to data.json
    with open('data.json', 'w') as file:
        json.dump(data, file, indent=4)
    
    return json_string

if __name__=="__main__":
    print(get_spending_tip())
