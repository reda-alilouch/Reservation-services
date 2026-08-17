import type { ComponentPropsWithRef } from "react";

export interface InputProps extends ComponentPropsWithRef<"input"> {
  label?: string;
  error?: string;
  hint?: string;
  containerClassName?: string;
}

export function Input({
  id,
  name,
  label,
  error,
  hint,
  className = "",
  containerClassName = "",
  ref,
  ...props
}: InputProps) {
  const inputId = id ?? name;
  const descriptionId =
    inputId && (error || hint) ? `${inputId}-description` : undefined;

  return (
    <div className={`space-y-2 ${containerClassName}`}>
      {label && inputId && (
        <label
          htmlFor={inputId}
          className="text-label-md font-medium uppercase text-on-surface-variant"
        >
          {label}
        </label>
      )}
      <input
        ref={ref}
        id={inputId}
        name={name}
        aria-invalid={Boolean(error)}
        aria-describedby={descriptionId}
        className={`h-11 w-full rounded-md border bg-surface-container-lowest px-3 text-sm text-on-surface outline-none transition-colors placeholder:text-on-surface-variant/70 focus:border-navy focus:ring-2 focus:ring-navy/15 disabled:cursor-not-allowed disabled:bg-surface-container disabled:text-on-surface-variant ${error ? "border-error focus:border-error focus:ring-error/15" : "border-outline-variant"} ${className}`}
        {...props}
      />
      {(error || hint) && (
        <p
          id={descriptionId}
          className={`text-sm ${error ? "text-error" : "text-on-surface-variant"}`}
        >
          {error ?? hint}
        </p>
      )}
    </div>
  );
}
