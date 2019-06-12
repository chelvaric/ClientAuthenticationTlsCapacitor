declare module "@capacitor/core" {
    interface PluginRegistry {
        ClientCertAuthentication: ClientCertAuthenticationPlugin;
    }
}
export interface ClientCertAuthenticationPlugin {
    echo(options: {
        value: string;
    }): Promise<{
        value: string;
    }>;
    generateRsaKey(options: {
        keySize: number;
    }): Promise<{
        value: string;
    }>;
    generateCsr(options: {
        privateKey: string;
    }): Promise<{
        value: string;
    }>;
}
