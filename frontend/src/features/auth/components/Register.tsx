import { Button, Input } from "@/components/ui";

export default function Register() {
  return (
    <div className="flex min-h-full flex-col justify-center px-6 py-12 sm:px-6 lg:px-8">
      <form className="sm:mx-auto sm:w-full sm:max-w-md">
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
            required
          />
          <Input
            label="Nom"
            name="lastName"
            autoComplete="family-name"
            required
          />
        </div>
        <div className="mt-5 space-y-5">
          <Input
            label="Adresse e-mail"
            name="email"
            type="email"
            autoComplete="email"
            placeholder="nom@exemple.com"
            required
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
              name="role"
              defaultValue="CLIENT"
              className="h-11 w-full rounded-md border border-outline-variant bg-surface-container-lowest px-3 text-sm text-on-surface outline-none transition-colors focus:border-navy focus:ring-2 focus:ring-navy/15"
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
            required
          />
          <Input
            label="Confirmer le mot de passe"
            name="confirmPassword"
            type="password"
            autoComplete="new-password"
            placeholder="••••••••"
            required
          />
        </div>
        <label className="mt-5 flex items-start gap-3 text-sm leading-5 text-on-surface-variant">
          <input
            type="checkbox"
            name="terms"
            className="mt-0.5 size-4 rounded border-outline-variant accent-navy"
            required
          />
          J’accepte les conditions d’utilisation et la politique de
          confidentialité.
        </label>
        <Button type="submit" size="lg" className="mt-6 w-full">
          Créer mon compte
        </Button>
      </form>
    </div>
  );
}
