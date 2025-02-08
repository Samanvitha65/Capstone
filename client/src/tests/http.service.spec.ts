import { TestBed, inject } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { environment } from '../environments/environment.development';
import { HttpService } from '../services/http.service';
import { AuthService } from '../services/auth.service';

describe('HttpService', () => {
  let service: HttpService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [
        HttpService,
        { provide: AuthService, useValue: { getToken: () => 'mockToken' } }
      ]
    });
    service = TestBed.inject(HttpService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should retrieve investigations', () => {
    const mockResponse = [{ id: 1, name: 'Investigation 1' }, { id: 2, name: 'Investigation 2' }];

    service.getInvestigations().subscribe((response: any) => {
      expect(response).toEqual(mockResponse);
    });

    const req = httpMock.expectOne(`${environment.apiUrl}/api/investigator/investigations`);
    expect(req.request.method).toBe('GET');
    expect(req.request.headers.get('Content-Type')).toBe('application/json');
    expect(req.request.headers.get('Authorization')).toBe('Bearer mockToken');
    req.flush(mockResponse);
  });

  it('should retrieve claims by underwriter', () => {
    const underwriterId = 123;
    const mockResponse = [{ id: 1, description: 'Claim 1' }, { id: 2, description: 'Claim 2' }];

    service.getClaimsByUnderwriter(underwriterId).subscribe((response: any) => {
      expect(response).toEqual(mockResponse);
    });

    const req = httpMock.expectOne(`${environment.apiUrl}/api/underwriter/claims?underwriterId=${underwriterId}`);
    expect(req.request.method).toBe('GET');
    expect(req.request.headers.get('Content-Type')).toBe('application/json');
    expect(req.request.headers.get('Authorization')).toBe('Bearer mockToken');
    req.flush(mockResponse);
  });

  it('should retrieve claims by policyholder', () => {
    const policyholderId = 456;
    const mockResponse = [{ id: 1, description: 'Claim 1' }, { id: 2, description: 'Claim 2' }];

    service.getClaimsByPolicyholder(policyholderId).subscribe((response: any) => {
      expect(response).toEqual(mockResponse);
    });

    const req = httpMock.expectOne(`${environment.apiUrl}/api/policyholder/claims?policyholderId=${policyholderId}`);
    expect(req.request.method).toBe('GET');
    expect(req.request.headers.get('Content-Type')).toBe('application/json');
    expect(req.request.headers.get('Authorization')).toBe('Bearer mockToken');
    req.flush(mockResponse);
  });

  it('should retrieve all claims', () => {
    const mockResponse = [{ id: 1, description: 'Claim 1' }, { id: 2, description: 'Claim 2' }];

    service.getAllClaims().subscribe((response: any) => {
      expect(response).toEqual(mockResponse);
    });

    const req = httpMock.expectOne(`${environment.apiUrl}/api/adjuster/claims`);
    expect(req.request.method).toBe('GET');
    expect(req.request.headers.get('Content-Type')).toBe('application/json');
    expect(req.request.headers.get('Authorization')).toBe('Bearer mockToken');
    req.flush(mockResponse);
  });

  it('should retrieve all underwriters', () => {
    const mockResponse = [{ id: 1, name: 'Underwriter 1' }, { id: 2, name: 'Underwriter 2' }];

    service.GetAllUnderwriter().subscribe((response: any) => {
      expect(response).toEqual(mockResponse);
    });

    const req = httpMock.expectOne(`${environment.apiUrl}/api/adjuster/underwriters`);
    expect(req.request.method).toBe('GET');
    expect(req.request.headers.get('Content-Type')).toBe('application/json');
    expect(req.request.headers.get('Authorization')).toBe('Bearer mockToken');
    req.flush(mockResponse);
  });

  it('should update investigation', () => {
    const investigationId = 789;
    const details = { description: 'Updated Investigation' };
    const mockResponse = { success: true };

    service.updateInvestigation(details, investigationId).subscribe((response: any) => {
      expect(response).toEqual(mockResponse);
    });

    const req = httpMock.expectOne(`${environment.apiUrl}/api/investigator/investigation/${investigationId}`);
    expect(req.request.method).toBe('PUT');
    expect(req.request.headers.get('Content-Type')).toBe('application/json');
    expect(req.request.headers.get('Authorization')).toBe('Bearer mockToken');
    expect(req.request.body).toEqual(details);
    req.flush(mockResponse);
  });

  it('should create investigation', () => {
    const details = { description: 'New Investigation' };
    const mockResponse = { success: true };

    service.createInvestigation(details).subscribe((response: any) => {
      expect(response).toEqual(mockResponse);
    });

    const req = httpMock.expectOne(`${environment.apiUrl}/api/investigator/investigation`);
    expect(req.request.method).toBe('POST');
    expect(req.request.headers.get('Content-Type')).toBe('application/json');
    expect(req.request.headers.get('Authorization')).toBe('Bearer mockToken');
    expect(req.request.body).toEqual(details);
    req.flush(mockResponse);
  });

  it('should create claims', () => {
    const policyholderId = 123;
    const details = { description: 'New Claim' };
    const mockResponse = { success: true };

    service.createClaims(details, policyholderId).subscribe((response: any) => {
      expect(response).toEqual(mockResponse);
    });

    const req = httpMock.expectOne(`${environment.apiUrl}/api/policyholder/claim?policyholderId=${policyholderId}`);
    expect(req.request.method).toBe('POST');
    expect(req.request.headers.get('Content-Type')).toBe('application/json');
    expect(req.request.headers.get('Authorization')).toBe('Bearer mockToken');
    expect(req.request.body).toEqual(details);
    req.flush(mockResponse);
  });

  it('should update claims', () => {
    const claimId = 123;
    const details = { description: 'Updated Claim' };
    const mockResponse = { success: true };

    service.updateClaims(details, claimId).subscribe((response: any) => {
      expect(response).toEqual(mockResponse);
    });

    const req = httpMock.expectOne(`${environment.apiUrl}/api/adjuster/claim/${claimId}`);
    expect(req.request.method).toBe('PUT');
    expect(req.request.headers.get('Content-Type')).toBe('application/json');
    expect(req.request.headers.get('Authorization')).toBe('Bearer mockToken');
    expect(req.request.body).toEqual(details);
    req.flush(mockResponse);
  });

  it('should update claims status', () => {
    const claimId = 123;
    const status = 'Approved';
    const mockResponse = { success: true };

    service.updateClaimsStatus(status, claimId).subscribe((response: any) => {
      expect(response).toEqual(mockResponse);
    });

    const req = httpMock.expectOne(`${environment.apiUrl}/api/underwriter/claim/${claimId}/review?status=${status}`);
    expect(req.request.method).toBe('PUT');
    expect(req.request.headers.get('Content-Type')).toBe('application/json');
    expect(req.request.headers.get('Authorization')).toBe('Bearer mockToken');
    req.flush(mockResponse);
  });


  it('should login', () => {
    const details = { username: 'testuser', password: 'testpassword' };
    const mockResponse = { token: 'mockToken' };

    service.Login(details).subscribe((response: any) => {
      expect(response).toEqual(mockResponse);
    });

    const req = httpMock.expectOne(`${environment.apiUrl}/api/user/login`);
    expect(req.request.method).toBe('POST');
    expect(req.request.headers.get('Content-Type')).toBe('application/json');
    expect(req.request.body).toEqual(details);
    req.flush(mockResponse);
  });

  it('should register', () => {
    const details = { username: 'newuser', password: 'newpassword' };
    const mockResponse = { success: true };

    service.registerUser(details).subscribe((response: any) => {
      expect(response).toEqual(mockResponse);
    });

    const req = httpMock.expectOne(`${environment.apiUrl}/api/user/register`);
    expect(req.request.method).toBe('POST');
    expect(req.request.headers.get('Content-Type')).toBe('application/json');
    expect(req.request.body).toEqual(details);
    req.flush(mockResponse);
  });
});
