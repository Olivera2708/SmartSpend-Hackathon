from openai import OpenAI
import json

client = OpenAI(base_url="http://localhost:1234/v1", api_key="lm-studio")

with open("data.json") as file:
    transactions = json.load(file)

history_for_tips_month = [
    {"role": "system", "content":
     """
    You are able only to reply with one very short sentence (up to 10 words).
    You are professional finance advisor and you are giving great answers.
    Using the provided data you should generate tip for better managing income.
    Here is the data you should take into considiration: 
    """ + json.dumps(transactions)},
    {"role": "user", "content": "Give me a great tip for saving money."}
]

history_for_tips_category = [
    {"role": "system", "content": "You are able only to reply with one word"}
]

def generate_monthly_tip_response(history):
    completion = client.chat.completions.create(
        model="TheBloke/Llama-2-7B-Chat-GGUF",
        messages=history,
        temperature=50,
        stream=True
    )

    for chunk in completion:
        if chunk.choices[0].delta.content:
            print(chunk.choices[0].delta.content, end="", flush=True)
            # new_message["content"] += chunk.choices[0].delta.content

generate_monthly_tip_response(history_for_tips_month)

# while True:
#     completion = client.chat.completions.create(
#         model="TheBloke/Llama-2-7B-Chat-GGUF",
#         messages=history,
#         temperature=0.7,
#         stream=True,
#     )

#     new_message = {"role": "assistant", "content": ""}
    
#     for chunk in completion:
#         if chunk.choices[0].delta.content:
#             print(chunk.choices[0].delta.content, end="", flush=True)
#             new_message["content"] += chunk.choices[0].delta.content

#     history.append(new_message)
    
#     # Uncomment to see chat history
#     # import json
#     # gray_color = "\033[90m"
#     # reset_color = "\033[0m"
#     # print(f"{gray_color}\n{'-'*20} History dump {'-'*20}\n")
#     # print(json.dumps(history, indent=2))
#     # print(f"\n{'-'*55}\n{reset_color}")

#     print()
#     history.append({"role": "user", "content": input("> ")})