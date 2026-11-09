-- Insert roles if not exist (created_at/updated_at are NOT NULL in the entity; MySQL would otherwise store 0000-00-00)
INSERT IGNORE INTO roles (name, created_at, updated_at) VALUES
  ('ADMIN', NOW(), NOW()),
  ('TEACHER', NOW(), NOW()),
  ('HEAD_OF_DEPARTMENT', NOW(), NOW()),
  ('EXAM_CONTROLLER', NOW(), NOW()),
  ('HR_MANAGER', NOW(), NOW()),
  ('EMPLOYEE', NOW(), NOW()),
  ('READ_ONLY', NOW(), NOW());

-- Repair role rows created earlier without timestamps (zero dates make the JDBC driver throw on read)
UPDATE roles SET created_at = NOW(), updated_at = NOW()
WHERE CAST(created_at AS CHAR) LIKE '0000-00-00%' OR CAST(updated_at AS CHAR) LIKE '0000-00-00%';

-- Insert default admin user (password = BCrypt of "admin123")
-- BCrypt hash: $2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy
INSERT IGNORE INTO users (username, email, password, full_name, is_active, created_at, updated_at)
VALUES ('admin', 'admin@javaeduapp.com',
        '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
        'System Administrator', true, NOW(), NOW());

-- Link admin user to ADMIN role
INSERT IGNORE INTO user_roles (user_id, role_id)
SELECT u.id, r.id FROM users u, roles r
WHERE u.username = 'admin' AND r.name = 'ADMIN';
