# Barangay Service System

The **Barangay Service System** is a simple console-based application that simulates how residents submit service requests to their local barangay office. The system allows residents to request common barangay services while enabling staff members to manage, review, and process those requests.

The goal of the system is to demonstrate a basic **request submission and processing workflow**, similar to how barangay offices handle documents such as clearances, certificates, and other administrative services.

The program uses **Java for the main application logic** and a **JSON Server backend** to store and manage application data.

---

# System Overview

The system consists of two main user roles:

## 1. Resident
Residents represent barangay citizens who want to request services.

Residents can:
- Submit new service requests
- View all their submitted requests
- View request statuses
- View rejected requests and staff remarks
- Pay for approved requests
- View scheduled requests
- View the full status history of their requests

### Resident Menu
1. View My Requests

2. Submit New Request

3. Balance / Pay Requests

4. View Scheduled Request

5. View Rejected Request

6. View Requests Status History

0. Logout

---

## 2. Staff
Staff members represent barangay office personnel responsible for validating and processing resident requests.

Staff can:
- View all submitted requests from residents
- Validate submitted requests
- Accept or reject requests
- Assign schedules for approved and paid requests
- Mark requests as completed
- Delete requests when necessary

### Staff Menu
1. View All Requests

2. View Pending Requests (for validation)

3. View Requests by Status

4. Assign Schedule (for Paid requests)

5. Mark Completed

6. Delete Request

0. Logout


---

# System Features

## Resident Features
Residents interact with the system by submitting and tracking their requests.

Key features include:

- **Submit Request**  
  Residents can submit requests for barangay services.

- **View Requests**  
  Residents can see all requests they have submitted and their current status.

- **Payment System**  
  Once a request is approved, residents must complete payment before the request can be scheduled.

- **Senior Citizen Discount**  
  Residents classified as senior citizens automatically receive a **40% discount** on service fees.

- **Request History Tracking**  
  Residents can view a detailed status history of their requests.

---

## Staff Features
Staff members manage and process the requests submitted by residents.

Key features include:

- **Request Validation**  
  Staff review submitted requests and either accept or reject them.

- **Status Management**  
  Staff can track and filter requests by status (Pending, Approved, Rejected, Scheduled, Completed).

- **Schedule Assignment**  
  Staff assign a schedule for approved and paid requests.

- **Request Completion**  
  Once the resident claims the request, staff can mark it as completed.

- **Request Deletion**  
  Staff may remove rejected or completed requests from the records.

---

# Request Workflow

The system follows a simple lifecycle from request submission to completion:

### 1. Request Submission
A resident submits a service request through the system.

### 2. Staff Validation
Staff review the request and verify the submitted requirements (simulated in this system).

- If **Rejected**:
  - Staff provide remarks explaining the rejection.
  - Residents can view the remarks and submit a new request if necessary.

- If **Approved**:
  - The resident proceeds to payment.

### 3. Payment
The resident completes the payment for the service.

If the resident is a **Senior Citizen**, a **40% discount** is automatically applied to the total fee.

### 4. Schedule Assignment
Once payment is completed, staff assign a schedule for the request.

### 5. Request Completion
After the resident claims the requested document or service, staff mark the request as **Completed**.

Staff may optionally delete completed or rejected requests from the system records.

---

# Getting Started

## Running the Program

Follow these steps to run the system locally.

### 1. Install Requirements
Make sure you have the following installed:

- **Java**
- **Node.js**

### 2. Install JSON Server
Open a terminal and run:

npm install -g json-server

### 3. Navigate to the Project Folder

cd BarangayServiceSystem

### 4. Start the Backend Server

Run the following command:

json-server --watch data.json

or

npx json-server data.json

The server will start at:

http://localhost:3000

### 5. Run the Java Application

Open the project in your IDE (such as IntelliJ IDEA) and run:

Main.java

The program will start in the console and ask you to log in as either a **Resident** or **Staff**.

---

# Demo Accounts

Use the following accounts to test the system.

## Non-Senior Resident

Email: resident1@email.com

ID: 0001

## Senior Resident

Email: resident2@email.com

ID: 0002

## Staff

Email: staff1@email.com

ID: S001

---

# Backend Configuration

The system uses a **JSON Server** to simulate a backend API.

### Data File

data.json

This file stores:

- User accounts
- Service types
- Requirments
- Service requests
- Status histories
- Schedules

The Java application communicates with the JSON server through HTTP requests.

---

# Technologies Used

- **Java**
- **Node.js**
- **JSON Server**
  
---

# Notes

The system is a simplified simulation and does not handle real document uploads or payments. 
This project was created for academic purposes. While it is functional, there may still be some bugs, limitations, or areas that could be improved.
