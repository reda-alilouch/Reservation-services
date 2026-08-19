"use client";

import { zodResolver } from "@hookform/resolvers/zod";
import { useState } from "react";
import { useForm } from "react-hook-form";
import { Button, Input } from "@/components/ui";
import { ApiError, authApi } from "@/lib/api";
import { registerSchema, type RegisterFormValues } from "@/lib/validations";

export default function Register() {
  const [submitError, setSubmitError] = useState<string>();
  const {
    register,
    handleSubmit,
    formState: { errors, isSubmitting },
  } = useForm<RegisterFormValues>({
    resolver: zodResolver(registerSchema),
    defaultValues: { role: "CLIENT", terms: false },
  });

  const onSubmit = async ({ terms: _terms, ...values }: RegisterFormValues) => {
    setSubmitError(undefined);
    try {
      await authApi.register(values);
      window.location.assign("/");
    } catch (error) {
      setSubmitError(
        error instanceof ApiError
          ? error.message
          : "La création du compte a échoué. Veuillez réessayer."
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
          Créer un compte
        </h2>
        <p className="mt-2 text-sm text-on-surface-variant">
          Créez votre espace en quelques instants.
        </p>
        <div className="mt-8 grid gap-5 sm:grid-cols-2">
          <Input
            label="Prénom"
            name="firstName"
            autoComplete="given-name"
            error={errors.firstName?.message}
            {...register("firstName")}
          />
          <Input
            label="Nom"
            name="lastName"
            autoComplete="family-name"
            error={errors.lastName?.message}
            {...register("lastName")}
          />
        </div>
        <div className="mt-5 space-y-5">
          <Input
            label="Adresse e-mail"
            name="email"
            type="email"
            autoComplete="email"
            placeholder="nom@exemple.com"
            error={errors.email?.message}
            {...register("email")}
          />
          <div className="space-y-2">
            <label
              htmlFor="role"
              className="text-label-md font-medium uppercase text-on-surface-variant"
            >
              Type de compte
            </label>
            <select
              id="role"
              className="h-11 w-full rounded-md border border-outline-variant bg-surface-container-lowest px-3 text-sm text-on-surface outline-none transition-colors focus:border-navy focus:ring-2 focus:ring-navy/15"
              {...register("role")}
            >
              <option value="CLIENT">Client</option>
              <option value="PRESTATAIRE">Prestataire</option>
            </select>
          </div>
          <Input
            label="Mot de passe"
            name="password"
            type="password"
            autoComplete="new-password"
            placeholder="8 caractères minimum"
            minLength={8}
            error={errors.password?.message}
            {...register("password")}
          />
          <Input
            label="Confirmer le mot de passe"
            name="confirmPassword"
            type="password"
            autoComplete="new-password"
            placeholder="••••••••"
            error={errors.confirmPassword?.message}
            {...register("confirmPassword")}
          />
        </div>
        <label className="mt-5 flex items-start gap-3 text-sm leading-5 text-on-surface-variant">
          <input
            type="checkbox"
            className="mt-0.5 size-4 rounded border-outline-variant accent-navy"
            aria-invalid={Boolean(errors.terms)}
            {...register("terms")}
          />
          J’accepte les conditions d’utilisation et la politique de
          confidentialité.
        </label>
        {errors.terms && (
          <p className="mt-2 text-sm text-error" role="alert">
            {errors.terms.message}
          </p>
        )}
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
          Créer mon compte
        </Button>
      </form>
    </div>
  );
}
