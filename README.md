# 📚 Smart Study Planner & Tracker (Java)

## 📌 Overview
Smart Study Planner is a Java-based console application that helps students efficiently plan and manage their daily study schedule. It prioritizes subjects based on urgency and workload, and dynamically adjusts plans based on user progress.

---

## 🚀 Features
- Add and manage multiple subjects  
- Priority-based scheduling (based on hours required & days left)  
- Automatic study time allocation  
- Urgent subject handling (deadline-aware logic)  
- Adaptive planning (updates next day plan if tasks are incomplete)  
- Input validation for error-free usage  
- Color-coded console output for better readability  

---

## 🧠 How It Works
- Each subject is assigned a **priority score** based on:
  - Hours required  
  - Days left  
- Subjects are sorted by priority  
- Study time is distributed proportionally  
- Urgent tasks are handled with higher weight  
- If the plan is not completed, the system adjusts:
  - Remaining hours are carried forward  
  - Days left are reduced  
  - New plan is generated  

---

## 🛠️ Tech Stack
- Java  
- Object-Oriented Programming (OOP)  
- ArrayList & Sorting  
- Console-based UI  

---

## ▶️ How to Run
1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/smart-study-planner-java.git
