import { useEffect, useState } from "react";
import { useAuth } from "../../context/AuthContext";
import { getAllExercises } from "../../services/exerciseApi.js";
import Sidebar from "./Sidebar.jsx";
import ExerciseCard from "../../components/ExerciseCard.jsx";
import "../../assets/css/ExerciseCatalog.css";

export default function ExerciseCatalog() {
  const { accessToken } = useAuth();
  const [exercises, setExercises] = useState([]);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);

  useEffect(() => {
    async function loadExercises() {
      try {
        const data = await getAllExercises(accessToken, page, 30);

        setTotalPages(data.totalPages);
        setExercises(data.content);
      } catch (error) {
        console.error(error);
      }
    }
    loadExercises();
  }, [accessToken, page]);

  return (
    <div className="client-layout">
      <Sidebar />

      <main className="exercise-content">
        <h1>Browse Exercises</h1>
        <p>Find exercises that make you feel motivated</p>

        <p>{exercises.length} exercises on this page</p>
        <div className="exercise-grid">
          {exercises.map((exercise) => (
            <ExerciseCard key={exercise.id} exercise={exercise} />
          ))}
        </div>

        <div className="pagination">
          <button disabled={page === 0} onClick={() => setPage(page - 1)}>
            Previous
          </button>

          <span>
            Page {page + 1} of {totalPages}
          </span>

          <button
            disabled={page === totalPages - 1}
            onClick={() => setPage(page + 1)}
          >
            Next
          </button>
        </div>
      </main>
    </div>
  );
}
