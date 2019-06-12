
  Pod::Spec.new do |s|
    s.name = 'ClientCertAuthentication'
    s.version = '0.0.1'
    s.summary = 'a plugin that generates keys and certificate signing requests to be send to a ca'
    s.license = 'MIT'
    s.homepage = 'https://github.com/chelvaric/ClientAuthenticationTlsCapacitor'
    s.author = 'Willems Wouter'
    s.source = { :git => 'https://github.com/chelvaric/ClientAuthenticationTlsCapacitor', :tag => s.version.to_s }
    s.source_files = 'ios/Plugin/**/*.{swift,h,m,c,cc,mm,cpp}'
    s.ios.deployment_target  = '11.0'
    s.dependency 'Capacitor'
  end