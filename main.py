import json
from llamaapi import LlamaAPI
from openai import OpenAI

# Initialize the SDK
llama = LlamaAPI("LL-Z1MNZGxiDuLn5B0jNrIpf9NTQo2kEJf5V9mVqE6Nkdqi4dY6aE6ZMN2MLVMV7gjk")

client = OpenAI(api_key = "LL-Z1MNZGxiDuLn5B0jNrIpf9NTQo2kEJf5V9mVqE6Nkdqi4dY6aE6ZMN2MLVMV7gjk", base_url = "https://api.llama-api.com")

def get_spending_tip():
    response = client.chat.completions.create(
        model="llama-13b-chat",
        messages=[
            {"role": "system", "content": "Assistant is a large language model trained by OpenAI."},
            {"role": "user", "content": "Give me an original financial tip for monthly spending up to 10 words"}
        ],
        temperature=2
    )
    return response.choices[0].message.content

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



if __name__=="__main__":
    print(get_category("Coffee with friends"))