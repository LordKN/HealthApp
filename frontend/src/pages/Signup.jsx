import "../assets/css/signup.css";
import { useState } from "react";
import { createClient, createCoach } from "../services/api.js";

export default function Signup() {
  const [userType, setUserType] = useState("");
  const [formData, setFormData] = useState({});

  function handleInputChange(e) {
    setFormData({
      ...formData,
      [e.target.id]: e.target.value,
    });
  }

  /*
   * Every input uses handleInputChange so React knows when the user changes a field
   *
   * React does NOT automatically read data from HTML inputs.
   * Instead, each onChange event sends a new value to the formData state
   *
   *  Example:
   * User types "Nguyen" into the name field
   *      ↓
   * onChange fires
   *      ↓
   * handleInputChange() updates formData.name
   *      ↓
   * React re-renders with the latest state
   *
   * Without onChange, formData would never update and the backend
   * would receive empty or outdated values.
   */

  async function handleSubmit(e) {
    e.preventDefault();
    console.log(formData);
    try {
      if (userType === "COACH") {
        await createCoach({
          ...formData,
          role: "COACH",
          yearsOfExperience: 0,
          clientCount: 0,
          openForNewClient: true,
        });
      } else if (userType === "CLIENT") {
        await createClient({
          ...formData,
          role: "CLIENT",
          height: Number(formData.height),
          weight: Number(formData.weight),
          bodyFat: Number(formData.bodyFat),
          availability: formData.availability,
        });
        alert("Account created successfully! You can now log in.");
      }
    } catch (error) {
      console.error("Error creating user:", error);
    }
  }

  return (
    <div>
      <form className="sign-up-container" onSubmit={handleSubmit}>
        <p>
          <strong>Name: </strong> <label htmlFor="name"></label>
          <input type="text" id="name" required onChange={handleInputChange} />
        </p>
        <p>
          <strong>Birthdate: </strong> <label htmlFor="date"></label>
          <input type="date" id="birthDate" required onChange={handleInputChange} />
        </p>
        <p>
          <strong>Sex: </strong>
          <label>
            <select className="rating" id="sex" onChange={handleInputChange}>
              <option value="">Select...</option>
              <option value="M">Male</option>
              <option value="F">Female</option>
            </select>
          </label>
        </p>
        <p>
          <strong>Address: </strong> <label htmlFor="address"></label>
          <input
            type="text"
            id="address"
            required
            onChange={handleInputChange}
          />
        </p>
        <p>
          <strong>Phone Number: </strong> <label htmlFor="phoneNo"></label>
          <input
            type="tel"
            id="phoneNo"
            required
            onChange={handleInputChange}
          />
        </p>
        <p>
          <strong>Email: </strong> <label htmlFor="email"></label>
          <input
            type="email"
            id="email"
            required
            onChange={handleInputChange}
          />
        </p>
        {/*
          <p>
          <strong>Your photo: </strong>{" "}
          <input
            type="file"
            accept="image/*"
            id="photo"
            required
            onChange={handleInputChange}
          />
        </p>
        */}

        <p>
          <strong>Emergency contact name: </strong>{" "}
          <label htmlFor="emergencyContactName"></label>
          <input
            type="text"
            id="emergencyContactName"
            required
            onChange={handleInputChange}
          />
        </p>
        <p>
          <strong>Emergency contact phone number: </strong>{" "}
          <label htmlFor="emergencyContactPhone"></label>
          <input
            type="tel"
            id="emergencyContactPhone"
            required
            onChange={handleInputChange}
          />
        </p>
        <p>
          <strong>Your relationship: </strong>{" "}
          <label htmlFor="emergencyContactRelationship"></label>
          <input
            type="text"
            id="emergencyContactRelationship"
            required
            onChange={handleInputChange}
          />
        </p>

        <p>
          <label htmlFor="userType">
            <strong>I am signing up as a:</strong>
          </label>

          <select
            id="userType"
            value={userType}
            onChange={(e) => setUserType(e.target.value)}
          >
            <option value="">Select...</option>
            <option value="COACH">Coach</option>
            <option value="CLIENT">Client</option>
          </select>
        </p>

        {userType === "COACH" && (
          <div id="coachQuestions" className="question-set">
            <label htmlFor="specialty">
              <strong>What's your specialty?</strong>
            </label>
            <select id="specialty" onChange={handleInputChange}>
              <option value="">SELECT...</option>
              <option value="STRENGTH_TRAINING">Strength Training</option>
              <option value="WEIGHT_LOSS">Weight Loss</option>
              <option value="REHAB">Rehab</option>
              <option value="CARDIO">Cardio</option>
            </select>

            <label htmlFor="workplace">
              <strong>What's your workplace?</strong>
            </label>
            <input
                type="text"
                id="workPlace"
                required
                onChange={handleInputChange}
            />

            <label htmlFor="description">
              <strong>Give your client a hook about yourself</strong>
            </label>
            <input
                type="text"
                id="description"
                required
                onChange={handleInputChange}
            />
          </div>
        )}

        {userType === "CLIENT" && (
          <div id="clientQuestions" className="question-set">
            <p>
              <label htmlFor="activityLevel">
                <strong>What's your activity level?</strong>
              </label>
              <select id="activityLevel" onChange={handleInputChange}>
                <option value="">SELECT...</option>
                <option value="LOW">Low</option>
                <option value="MEDIUM">Medium</option>
                <option value="HIGH">High</option>
              </select>
            </p>

            <p>
              <label htmlFor="fitnessGoal">
                <strong>What's your fitness goal?</strong>
              </label>
              <select id="fitnessGoal" onChange={handleInputChange}>
                <option value="">SELECT...</option>
                <option value="WEIGHT_LOSS">Weight Loss</option>
                <option value="MUSCLE_GAIN">Muscle Gain</option>
                <option value="MAINTENANCE">Maintenance</option>
                <option value="GENERAL_HEALTH">General Health</option>
              </select>
            </p>

            <p>
              <label htmlFor="sleepPattern">
                <strong>Sleep Quality:</strong>
              </label>
              <select
                id="sleepPattern"
                name="sleepPattern"
                onChange={handleInputChange}
              >
                <option value="">SELECT...</option>
                <option value="POOR">Poor</option>
                <option value="AVERAGE">Average</option>
                <option value="GOOD">Good</option>
              </select>
            </p>

            <p>
              <label htmlFor="height">
                <strong>Height (cm):</strong>
              </label>
              <input
                type="number"
                id="height"
                min="50"
                max="300"
                step="0.1"
                onChange={handleInputChange}
              ></input>
            </p>

            <p>
              <label htmlFor="weight">
                <strong>Weight (kg):</strong>
              </label>
              <input
                type="number"
                id="weight"
                onChange={handleInputChange}
                min="20"
                max="500"
                step="0.1"
              ></input>
            </p>

            <p>
              <label htmlFor="bodyFat">
                <strong>Body Fat %:</strong>
              </label>
              <input
                type="number"
                id="bodyFat"
                min="0"
                max="100"
                step="0.1"
                onChange={handleInputChange}
              ></input>
            </p>

            <p>
              <label htmlFor="availability">
                <strong>Availability:</strong>
              </label>
              <input
                type="text"
                id="availability"
                onChange={handleInputChange}
                placeholder="Mon/Wed/Fri evenings"
              ></input>
            </p>
          </div>
        )}

        <button type="submit" className="submit-button">
          Submit
        </button>
      </form>
    </div>
  );
}
