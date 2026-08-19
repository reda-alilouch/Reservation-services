"use client";

import { zodResolver } from "@hookform/resolvers/zod";
import { useState } from "react";
import { useForm } from "react-hook-form";
import { Button, Input } from "@/components/ui";
import { ApiError, authApi } from "@/lib/api";
import { loginSchema, type LoginFormValues } from "@/lib/validations";

export default function Login() {
  const [submitError, setSubmitError] = useState<string>();
  const {
    register,
    handleSubmit,
    formState: { errors, isSubmitting },
  } = useForm<LoginFormValues>({ resolver: zodResolver(loginSchema) });

  const onSubmit = async (values: LoginFormValues) => {
    setSubmitError(undefined);
    try {
      await authApi.login(values);
      window.location.assign("/");
    } catch (error) {
      setSubmitError(
        error instanceof ApiError
          ? error.message
          : "La connexion a échoué. Veuillez réessayer."
      );
    }
  };

  return (
    <div className="flex min-h-full flex-col justify-center px-6 py-12 sm:px-6 lg:px-8">
      <form
        className="sm:mx-auto sm:w-full sm:max-w-md"
        noValidate
        onSubmit={handleSubmit(onSubmit)}
      >
        <h2 className="text-headline-sm font-semibold text-navy">
          Se connecter
        </h2>
        <p className="mt-2 text-sm text-on-surface-variant">
          Accédez à votre espace de réservation.
        </p>
        <div className="mt-8 space-y-5">
          <Input
            label="Adresse e-mail"
            name="email"
            type="email"
            autoComplete="email"
            placeholder="nom@exemple.com"
            error={errors.email?.message}
            {...register("email")}
          />
          <Input
            label="Mot de passe"
            name="password"
            type="password"
            autoComplete="current-password"
            placeholder="••••••••"
            error={errors.password?.message}
            {...register("password")}
          />
        </div>
        <div className="mt-3 flex justify-end">
          <button
            type="button"
            className="text-sm font-medium text-secondary hover:text-gold"
          >
            Mot de passe oublié ?
          </button>
        </div>
        {submitError && (
          <p className="mt-4 text-sm text-error" role="alert">
            {submitError}
          </p>
        )}
        <Button
          type="submit"
          size="lg"
          className="mt-6 w-full"
          isLoading={isSubmitting}
        >
          Se connecter
        </Button>
      </form>
    </div>
  );
}
