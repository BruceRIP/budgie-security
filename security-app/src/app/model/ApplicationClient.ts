export class ApplicationClient {
  applicationName: string;
  clientId: string;
  clientSecret: string;
  clientAccessToken: string;
  resourceIds: string[];
  scope: string[];
  authorizationGrantTypes: string[];
  redirectUris: string[];
  authorities: string[];
  autoApprove: boolean;
  accessTokenValidity: number;
  refreshTokenValidity: number;
}
