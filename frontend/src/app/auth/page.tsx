"use client";

import { useState } from "react";
import Login from "@/features/auth/components/Login";
import Register from "@/features/auth/components/Register";

type AuthMode = "login" | "register";

export default function AuthPage() {
  const [mode, setMode] = useState<AuthMode>("login");

  return (
    <main className="min-h-screen bg-surface-container-low px-4 py-10 sm:px-6">
      <div className="mx-auto w-full max-w-xl overflow-hidden rounded-xl border border-outline-variant bg-surface-container-lowest shadow-sm">
        <div className="grid grid-cols-2 border-b border-outline-variant p-2">
          <button
            type="button"
            onClick={() => setMode("login")}
            className={`rounded-md px-4 py-3 text-sm font-semibold transition-colors ${mode === "login" ? "bg-navy text-white" : "text-on-surface-variant hover:bg-surface-container hover:text-navy"}`}
            aria-pressed={mode === "login"}
          >
            Se connecter
          </button>
          <button
            type="button"
            onClick={() => setMode("register")}
            className={`rounded-md px-4 py-3 text-sm font-semibold transition-colors ${mode === "register" ? "bg-navy text-white" : "text-on-surface-variant hover:bg-surface-container hover:text-navy"}`}
            aria-pressed={mode === "register"}
          >
            Créer un compte
          </button>
        </div>
        {mode === "login" ? <Login /> : <Register />}
      </div>
    </main>
  );
}
