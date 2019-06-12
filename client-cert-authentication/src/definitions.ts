declare module "@capacitor/core" {
  interface PluginRegistry {
    ClientCertAuthentication: ClientCertAuthenticationPlugin;
  }
}

export interface ClientCertAuthenticationPlugin {
  echo(options: { value: string }): Promise<{value: string}>;
}
