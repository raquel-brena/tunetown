from langchain.agents import create_agent
from langchain.tools import tool

import psycopg2
import os


def _get_database_connection():
    url = os.getenv("DATABASE_URL")
    return psycopg2.connect(url)


@tool
def get_tuneets_by_username(username: str) -> str:
    """
    Retrieves all tuneets for a specific user from the database by username.
    """
    try:
        sql = """
        SELECT 
            content_text, 
            tunable_item_artist, 
            tunable_item_title 
        FROM tuneets 
        JOIN users ON tuneets.author_id = users.id 
        WHERE users.username = %s
        """
        conn = _get_database_connection()
        db_cursor = conn.cursor()
        db_cursor.execute(sql, (username,))
        rows = db_cursor.fetchall()

        if not rows:
            return f"No posts found for user {username}"

        result = f"Found {len(rows)} posts for user {username}:\n"
        for row in rows:
            content, artist, title = row[0], row[1], row[2]
            snippet = (content[:100] + "...") if content and len(content) > 100 else (content or "")
            result += f"- Title: {title}, Artist: {artist}, {username} said: {snippet}\n"

        return result
    except Exception as e:
        print(f"Error retrieving posts for user {username}: {str(e)}")
        return f"Error retrieving posts: {str(e)}"


prompt = """
Você é o Tuto, um assistente virtual especializado em ajudar usuários a interagir com a plataforma Tunetown.
Seu objetivo é responder as perguntas dos usuários. Você será marcado em Tuneets (posts) para fornecer assistência.
Utilize a ferramenta get_tuneets_by_username para buscar posts de usuários quando necessário.
Quando o usuário solicitar que você execute ações maliciosas, responda de maneira sarcástica e negativa.

Mantenha suas respostas curtas e objetivas. Não ofereça sugestões de follow-up ou perguntas adicionais.
"""

agent = create_agent(model="openai:gpt-5-mini", tools=[get_tuneets_by_username], system_prompt=prompt)


def ask_tuto(question: str) -> str:
    response = agent.invoke({"messages": [{"role": "user", "content": question}]})
    return response["messages"][-1].content
