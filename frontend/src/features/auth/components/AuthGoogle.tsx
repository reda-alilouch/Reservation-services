import { Chrome } from "lucide-react";
import { Button } from "@/components/ui";

export default function AuthGoogle() {

  
  return (
    <Button
      type="button"
      variant="ghost"
      size="md"
      className="group w-full gap-3 border border-outline-variant bg-surface-container-lowest text-navy shadow-sm transition-all hover:border-navy/40 hover:bg-surface-container-low focus-visible:ring-navy active:scale-[0.99]"
    >
      <span className="flex size-6 items-center justify-center rounded-full bg-white text-[#4285F4] shadow-sm ring-1 ring-black/5">
        <Chrome
          aria-hidden="true"
          className="size-4 transition-transform group-hover:rotate-12"
          strokeWidth={2.5}
        />
      </span>
      Continuer avec Google
    </Button>
  );
}
