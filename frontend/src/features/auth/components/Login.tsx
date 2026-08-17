import { Button, Input } from "@/components/ui";

export default function Login() {
  return (
    <div className="flex min-h-full flex-col justify-center px-6 py-12 sm:px-6 lg:px-8">
      <form className="sm:mx-auto sm:w-full sm:max-w-md">
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
            required
          />
          <Input
            label="Mot de passe"
            name="password"
            type="password"
            autoComplete="current-password"
            placeholder="••••••••"
            required
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
        <Button type="submit" size="lg" className="mt-6 w-full">
          Se connecter
        </Button>
      </form>
    </div>
  );
}
