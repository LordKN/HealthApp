import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Home from "./pages/Home";
import About from "./pages/About";
import Login from "./pages/Login";

import Signup from "./pages/Signup";
function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/about" element={<About />} />
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<Signup />} />
      </Routes>
    </Router>
  );
}

export default App;

/*
 * DEFAULT EXPORT
 * --------------------------------------------------------------------
 * Use "export default" when this file has ONE main component.
 *
 * Import Without curly braces:
 *      import Footer from "./Footer";
 *
 * The imported name can be anything:
 *      import MyFooter from "./Footer";
 *
 * Default exports are commonly used for React page and UI components.
 */

/*
 * NAME EXPORT
 * --------------------------------------------------------------------
 * Use a named export when a file exports MULTIPLE items.
 *
 * Example:
 *      export function Footer() {}
 *      export function Navbar() {}
 *
 * Import with curly braces:
 *      import { Footer, Navbar } from "./components";
 *
 * The imported name MUST match the exported name exactly.
 */

/*
 * React Router uses client-side routing
 *
 * Instead of requesting a new HTML page from Spring Boot,
 * React changes the displayed component inside the browser.
 *
 * This creates a faster user experience because the page
 * does not fully reload during the navigation
 *
 * Spring Boot only handles API requests, while
 * React handles page navigations
 */
