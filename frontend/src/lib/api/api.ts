const API_URL = process.env.NEXT_PUBLIC_API_URL ?? "http://localhost:8080/api";

export class ApiError extends Error {
  constructor(
    message: string,
    public readonly status: number
  ) {
    super(message);
    this.name = "ApiError";
  }
}

type ApiOptions = Omit<RequestInit, "body"> & { body?: unknown };

function getErrorMessage(body: unknown, fallback: string) {
  if (typeof body === "object" && body !== null && "message" in body) {
    const { message } = body as { message?: unknown };
    if (typeof message === "string") return message;
  }
  return fallback;
}

export async function api<T>(
  path: string,
  options: ApiOptions = {}
): Promise<T> {
  const { body, headers, ...init } = options;
  const response = await fetch(`${API_URL}${path}`, {
    ...init,
    body: body === undefined ? undefined : JSON.stringify(body),
    credentials: "include",
    headers: {
      Accept: "application/json",
      ...(body === undefined ? {} : { "Content-Type": "application/json" }),
      ...headers,
    },
  });

  if (response.status === 204) return undefined as T;

  const responseBody: unknown = await response.json().catch(() => undefined);
  if (!response.ok) {
    throw new ApiError(
      getErrorMessage(responseBody, `Erreur HTTP ${response.status}`),
      response.status
    );
  }
  return responseBody as T;
}
