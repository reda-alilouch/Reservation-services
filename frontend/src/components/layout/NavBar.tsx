"use client";

import { Hotel, Menu, User, X } from "lucide-react";
import Link from "next/link";
import { usePathname } from "next/navigation";
import { useState } from "react";

export default function NavBar() {
  const [isMenuOpen, setIsMenuOpen] = useState(false);
  const pathname = usePathname();

  const navItems = [
    {
      name: "Accueil",
      href: "/",
    },
    {
      name: "Services",
      href: "/services",
    },
    {
      name: "A propos",
      href: "/about",
    },
    {
      name: "Contact",
      href: "/contact",
    },
  ];

  const isActive = (href: string) =>
    href === "/" ? pathname === href : pathname.startsWith(href);

  return (
    <header className="border-b border-outline-variant bg-surface-container-lowest">
      <nav
        className="mx-auto flex min-h-16 max-w-7xl items-center justify-between px-4 py-3 sm:px-6 lg:px-8"
        aria-label="Navigation principale"
      >
        <Link
          href="/"
          className="group inline-flex items-center gap-2 text-title-md font-semibold text-navy"
          onClick={() => setIsMenuOpen(false)}
        >
          <span className="grid size-9 place-items-center rounded-md bg-navy text-gold transition-transform group-hover:scale-105">
            <Hotel className="size-5" aria-hidden="true" />
          </span>
        </Link>

        <div className="hidden items-center gap-1 md:flex">
          {navItems.map((item) => (
            <Link
              key={item.href}
              href={item.href}
              aria-current={isActive(item.href) ? "page" : undefined}
              className={`rounded-md px-3 py-2 text-sm font-medium transition-colors ${isActive(item.href) ? "bg-secondary-container/45 text-secondary" : "text-on-surface-variant hover:bg-surface-container hover:text-navy"}`}
            >
              {item.name}
            </Link>
          ))}
        </div>

        <div className="hidden items-center gap-3 md:flex">
          <Link
            href="/auth"
            className="inline-flex h-11 items-center justify-center rounded-md bg-navy px-4 text-sm font-semibold text-white transition-colors hover:bg-primary-container focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-navy focus-visible:ring-offset-2"
          >
            <User className="size-4" aria-hidden="true" />
          </Link>
         
        </div>

        <button
          type="button"
          className="inline-flex size-10 items-center justify-center rounded-md text-navy transition-colors hover:bg-surface-container focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-navy md:hidden"
          aria-label={isMenuOpen ? "Fermer le menu" : "Ouvrir le menu"}
          aria-expanded={isMenuOpen}
          onClick={() => setIsMenuOpen((open) => !open)}
        >
          {isMenuOpen ? (
            <X className="size-5" aria-hidden="true" />
          ) : (
            <Menu className="size-5" aria-hidden="true" />
          )}
        </button>
      </nav>

      {isMenuOpen && (
        <div className="border-t border-outline-variant bg-surface-container-lowest px-4 py-3 md:hidden">
          <div className="mx-auto flex max-w-7xl flex-col gap-1">
            {navItems.map((item) => (
              <Link
                key={item.href}
                href={item.href}
                aria-current={isActive(item.href) ? "page" : undefined}
                onClick={() => setIsMenuOpen(false)}
                className={`rounded-md px-3 py-2.5 text-sm font-medium transition-colors ${isActive(item.href) ? "bg-secondary-container/45 text-secondary" : "text-on-surface-variant hover:bg-surface-container hover:text-navy"}`}
              >
                {item.name}
              </Link>
            ))}
            <Link
              href="/auth"
              onClick={() => setIsMenuOpen(false)}
              className="mt-2 inline-flex h-11 items-center justify-center rounded-md bg-navy px-4 text-sm font-semibold text-white transition-colors hover:bg-primary-container"
            >
              Se connecter
            </Link>
            <Link
              href="/auth"
              onClick={() => setIsMenuOpen(false)}
              className="inline-flex h-11 items-center justify-center rounded-md border border-gold px-4 text-sm font-semibold text-secondary transition-colors hover:bg-secondary-container/30"
            >
              Créer un compte
            </Link>
          </div>
        </div>
      )}
    </header>
  );
}
