import React from "react";
import { RouterProvider, createBrowserRouter } from "react-router-dom";
import Navbar from "./components/Navbar/index.jsx";
import Home from "./pages/Home/index.jsx";
import UploadFile from "./pages/UploadFile/index.jsx";
import SearchFile from "./pages/SearchFile/index.jsx";
import Login from "./pages/Login/index.jsx";
import Register from "./pages/Register/index.jsx";

const router = createBrowserRouter([
  {
    path: "/",
    element: <Home />,
  },
  {
    path: "/upload",
    element: <UploadFile />,
  },
  {
    path: "/search",
    element: <SearchFile />,
  },
  {
    path: "/login",
    element: <Login />,
  },
  {
    path: "/register",
    element: <Register />,
  },
]);

const App = () => {
  return (
    <RouterProvider router={router}>
      <Navbar />
    </RouterProvider>
  );
};

export default App;
