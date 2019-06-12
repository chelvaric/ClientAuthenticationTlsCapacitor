declare module "@capacitor/core" {
  interface PluginRegistry {
    ClientCertAuthentication: ClientCertAuthenticationPlugin;
  }
}

export interface ClientCertAuthenticationPlugin {
  echo(options: { value: string }): Promise<{value: string}>;
  generateRsaKey(options: { keySize: number }) : Promise<{privateKey:string,publicKey:string}>;
  generateCsr(options: { privateKey: string, publicKey: string, commonName:string }): Promise<{value:string}>;
}
