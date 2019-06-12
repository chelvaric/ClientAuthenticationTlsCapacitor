import { WebPlugin } from '@capacitor/core';
import { ClientCertAuthenticationPlugin } from './definitions';

export class ClientCertAuthenticationWeb extends WebPlugin implements ClientCertAuthenticationPlugin {
 
 
  constructor() {
    super({
      name: 'ClientCertAuthentication',
      platforms: ['web']
    });
  }

  async echo(options: { value: string }): Promise<{value: string}> {
    console.log('ECHO', options);
    return options;
  }

  async generateRsaKey(options: { keySize: number; }): Promise<{ value: string; }> {
    console.log('generate rsa key', options);
    return {value: "key"};
  }
  async generateCsr(options: { privateKey: string; }): Promise<{ value: string; }> {
    console.log('generate csr key', options);
    return {value: "csr"};
  }

 
}

const ClientCertAuthentication = new ClientCertAuthenticationWeb();

export { ClientCertAuthentication };

import { registerWebPlugin } from '@capacitor/core';
registerWebPlugin(ClientCertAuthentication);
