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
    return remove_emojis(response.choices[0].message.content.split(".")[0].replace("\"", ""))

def get_category_tip():
    with open("data.json") as file:
        data = json.load(file)
    response = client.chat.completions.create(
        model="llama-13b-chat",
        messages=[
                {"role": "system", "content":
                    "You can reply with one short sentence. You are a finance advisor providing tips for better managing income based on the data user gives."},
                {"role": "user", "content": "Give me a very short tip, up to 10 words based on my data. Remove all the unnecessary words in your response. My data is: " + json.dumps(data)}
        ],
        temperature=1
    )
    return response.choices[0].message.content

def get_category(word):
    response = client.chat.completions.create(
        model="llama-13b-chat",
        messages=[
                {"role": "system", "content":
                    "You can reply with one of the following words: GROCERIES, BILLS, TRAVEL, TRANSPORT, FUN, OTHER"},
                {"role": "user", "content": "put " + word + " in one of the categories (GROCERIES, BILLS, TRAVEL, TRANSPORT, FUN, OTHER). Reply with just the category"}
        ],
        temperature=0.2
    )
    return response.choices[0].message.content


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
    response = client.chat.completions.create(
        model="llama-13b-chat",
        messages=[
                {"role": "system", "content":
                    "You are a financial asistant. You always give an intellingent answer to all of the requests your client gives. You take into the consideration the users data."},
                {"role": "user", "content": request + "My data is " + json.dumps(data)}
        ],
        temperature=0.2
    )
    return response.choices[0].message.content

if __name__=="__main__":
    print(chat("I want to buy a car. Should I do it?"))
