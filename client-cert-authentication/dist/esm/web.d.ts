import { WebPlugin } from '@capacitor/core';
import { ClientCertAuthenticationPlugin } from './definitions';
export declare class ClientCertAuthenticationWeb extends WebPlugin implements ClientCertAuthenticationPlugin {
    constructor();
    echo(options: {
        value: string;
    }): Promise<{
        value: string;
    }>;
    generateRsaKey(options: {
        keySize: number;
    }): Promise<{
        privateKey: string;
        publicKey: string;
    }>;
    generateCsr(options: {
        privateKey: string;
        publicKey: string;
        commonName: string;
    }): Promise<{
        value: string;
    }>;
}
declare const ClientCertAuthentication: ClientCertAuthenticationWeb;
export { ClientCertAuthentication };
