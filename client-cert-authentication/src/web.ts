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

  async generateRsaKey(options: { keySize: number; }): Promise<{ privateKey: string; publicKey: string }> {
    console.log('generate rsa key', options);
    return { privateKey: "key", publicKey:"test"};
  }

  generateCsr(options: { privateKey: string; publicKey: string; commonName: string; }): Promise<{ value: string; }> {
    
     console.log(options);
     return  null;
  }
  

 
}

const ClientCertAuthentication = new ClientCertAuthenticationWeb();

export { ClientCertAuthentication };

import { registerWebPlugin } from '@capacitor/core';
registerWebPlugin(ClientCertAuthentication);
