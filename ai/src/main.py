from fastapi import FastAPI
from pydantic import BaseModel
from dotenv import load_dotenv
from src.agent.tuto import ask_tuto

load_dotenv()


app = FastAPI()


class Question(BaseModel):
    question: str


@app.post("/tuto")
async def tuto(question: Question):
    response = ask_tuto(question.question)
    return {"response": response}
