--Usuarios
INSERT INTO Usuario(id, nombre, apellido, dni, direccion, email, contrasena)
VALUES(null, 'Jose', 'Lopez', 15012456, 'Florencio Varela 1903, San Justo, Provincia de Buenos Aires', 'jlopez@gmail.com','abcd1234');

--Productos
    --Cat. Almacen
INSERT INTO Producto (nombre, precio, codigoBarras, categoria, subcategoria, urlImagen, descuento)
VALUES
    ('Arroz Gallo Oro 1kg', 3750, '11450502019', 'Almacen', 'Arroz', 'https://jumboargentina.vtexassets.com/arquivos/ids/803457-1200-auto?v=638379921950070000&width=1200&height=auto&aspect=true', 3562.5),
    ('Fideos Don Vicente Tallarines 500g', 2000, '11460304020', 'Almacen', 'Pastas', 'https://jumboargentina.vtexassets.com/arquivos/ids/798895-1200-auto?v=638343636550800000&width=1200&height=auto&aspect=true', null),
    ('Aceite de Girasol Cocinero 1.5L', 2100, '12610905011', 'Almacen', 'Aceite', 'https://jumboargentina.vtexassets.com/arquivos/ids/614743-1200-auto?v=637409203109900000&width=1200&height=auto&aspect=true', null),
    ('Harina 0000 Pureza 1kg', 900, '11410142005', 'Almacen', 'Harina', 'https://jumboargentina.vtexassets.com/arquivos/ids/644481-1200-auto?v=637572504306700000&width=1200&height=auto&aspect=true', null),
    ('Azúcar Molida Ledesma 1kg', 1308, '11270101016', 'Almacen', 'Endulzantes', 'https://jumboargentina.vtexassets.com/arquivos/ids/703064-1200-auto?v=637891329664700000&width=1200&height=auto&aspect=true', null),

    --Cat. Perfumeria
    ('Shampoo Sedal Rizos Definidos 650ml', 5150, '13710375002', 'Perfumeria', 'Shampoo', 'https://jumboargentina.vtexassets.com/arquivos/ids/766775-1200-auto?v=638108302089300000&width=1200&height=auto&aspect=true', null),
    ('Acondicionador Sedal Ceramidas 650ml', 5200, '13720863004', 'Perfumeria', 'Acondicionador', 'https://jumboargentina.vtexassets.com/arquivos/ids/787029-1200-auto?v=638240818051470000&width=1200&height=auto&aspect=true', null),
    ('Desodorante Rexona Antibacterial 150ml', 2500, '13420110034', 'Perfumeria', 'Desodorantes', 'https://jumboargentina.vtexassets.com/arquivos/ids/802194-1200-auto?v=638375603688170000&width=1200&height=auto&aspect=true', null),
    ('Jabón Dove Go Original 90g', 1300, '13110109050', 'Perfumeria', 'Jabones', 'https://jumboargentina.vtexassets.com/arquivos/ids/806393-1200-auto?v=638403357956070000&width=1200&height=auto&aspect=true', null),
    ('Crema Dental Colgate Total 12 90g', 3315, '13220102208', 'Perfumeria', 'Dentifricos', 'https://jumboargentina.vtexassets.com/arquivos/ids/813235-1200-auto?v=638441267527100000&width=1200&height=auto&aspect=true', null),

    --Cat. Bebidas
    ('Agua Mineral Glaciar Sin Gas Bajo Sodio 1.5L', 1554, '12120102001', 'Bebidas', 'Agua', 'https://jumboargentina.vtexassets.com/arquivos/ids/545277-1200-auto?v=637014798216800000&width=1200&height=auto&aspect=true', null),
    ('Gaseosa Coca-Cola Original 2.25L', 3100, '12110101009', 'Bebidas', 'Gaseosas', 'https://jumboargentina.vtexassets.com/arquivos/ids/782825-1200-auto?v=638206689776800000&width=1200&height=auto&aspect=true', null),
    ('Jugo Cepita Fresh Naranja 1.5L', 1485, '12130595001', 'Bebidas', 'Jugos', 'https://jumboargentina.vtexassets.com/arquivos/ids/801011-1200-auto?v=638367069703130000&width=1200&height=auto&aspect=true', null),
    ('Vino tinto Elementos Malbec 750Cc', 3900, '12262130001', 'Bebidas', 'Vinos', 'https://jumboargentina.vtexassets.com/arquivos/ids/581089-1200-auto?v=637225176402330000&width=1200&height=auto&aspect=true', null),
    ('Cerveza Quilmes Clasica 1L', 1783, '12440106063', 'Bebidas', 'Cervezas', 'https://jumboargentina.vtexassets.com/arquivos/ids/433499-1200-auto?v=636517616933330000&width=1200&height=auto&aspect=true', null),

    -- Cat. Verduleria
    ('Tomate Redondo Grande 1kg', 4499, '23210315001', 'Verduleria', 'Tomate', 'https://jumboargentina.vtexassets.com/arquivos/ids/339429-1200-auto?v=636393043636870000&width=1200&height=auto&aspect=true', null),
    ('Manzana Roja 1kg', 2299, '23110102025', 'Verduleria', 'Manzana', 'https://jumboargentina.vtexassets.com/arquivos/ids/472481-1200-auto?v=636694698370130000&width=1200&height=auto&aspect=true', null),
    ('Lechuga Capuchina 1Kg', 3699, '23210120001', 'Verduleria', 'Lechuga', 'https://jumboargentina.vtexassets.com/arquivos/ids/450976-1200-auto?v=636577194077270000&width=1200&height=auto&aspect=true', null),
    ('Zanahoria 1kg', 1199, '23210512002', 'Verduleria', 'Zanahoria', 'https://jumboargentina.vtexassets.com/arquivos/ids/472800-1200-auto?v=636695562251270000&width=1200&height=auto&aspect=true', null),
    ('Papa Negra 1kg', 599, '23210509005', 'Verduleria', 'Papa', 'https://jumboargentina.vtexassets.com/arquivos/ids/449110-1200-auto?v=636567690053330000&width=1200&height=auto&aspect=true', null),

    -- Cat. Limpieza
    ('Lavandina Ayudín 1L', 699, '14160501064', 'Limpieza', 'Lavandina', 'https://jumboargentina.vtexassets.com/arquivos/ids/678535-1200-auto?v=637739049922700000&width=1200&height=auto&aspect=true', null),
    ('Detergente Líquido Magistral Limón 750ml', 3550, '14210196003', 'Limpieza', 'Detergente', 'https://jumboargentina.vtexassets.com/arquivos/ids/781293-1200-auto?v=638194917774670000&width=1200&height=auto&aspect=true', null),
    ('Esponja Clasica Scotch-Brite', 562, '43140757003', 'Limpieza', 'Esponjas', 'https://jumboargentina.vtexassets.com/arquivos/ids/529332-1200-auto?v=636898152780870000&width=1200&height=auto&aspect=true', null),
    ('Trapo de Piso Nido De Abeja 1 unidad', 2775, '43140174001', 'Limpieza', 'Trapos', 'https://jumboargentina.vtexassets.com/arquivos/ids/794169-1200-auto?v=638307562527270000&width=1200&height=auto&aspect=true', null),
    ('Guantes de Látex Mapa 8 1/2', 3787, '43150160004', 'Limpieza', 'Guantes', 'https://jumboargentina.vtexassets.com/arquivos/ids/527864-1200-auto?v=636890370760170000&width=1200&height=auto&aspect=true', null),

    -- Cat. Lacteos
    ('Leche Descremada La Serenísima 1L', 1362, '21110301058', 'Lacteos', 'Leche', 'https://jumboargentina.vtexassets.com/arquivos/ids/760437-1200-auto?v=638049118084400000&width=1200&height=auto&aspect=true', null),
    ('Manteca Tonadita 200g', 2250, '21270184001', 'Lacteos', 'Manteca', 'https://jumboargentina.vtexassets.com/arquivos/ids/176504-1200-auto?v=636383331571730000&width=1200&height=auto&aspect=true', null),
    ('Queso Parmesano La Paulina 1Kg', 12999, '24450703003', 'Lacteos', 'Quesos', 'https://jumboargentina.vtexassets.com/arquivos/ids/596261-1200-auto?v=637315026086830000&width=1200&height=auto&aspect=true', null),
    ('Yogur Yogurisimo Natural 300g', 1700, '21220166004', 'Lacteos', 'Yogures', 'https://jumboargentina.vtexassets.com/arquivos/ids/790645-1200-auto?v=638277969692900000&width=1200&height=auto&aspect=true', null),
    ('Crema Doble Tregar 350g', 2080, '21210150006', 'Lacteos', 'Crema', 'https://jumboargentina.vtexassets.com/arquivos/ids/774486-1200-auto?v=638150854086930000&width=1200&height=auto&aspect=true', null);

--Supermercados
--INSERT INTO Supermercado (
--    distanciaNumero, distanciaDescripcion, banderaId, lat, lng, sucursalNombre,
--    id, sucursalTipo, provincia, direccion, banderaDescripcion, localidad,
--    comercioRazonSocial, comercioId, sucursalId, logoUrl
--) VALUES
--(1.5, 'A 1.5 km', 1, '-34.748206', '-58.594244', 'Carrefour San Justo',
-- '1', 'Hipermercado', 'Buenos Aires', 'Av. Brig. Gral. Juan Manuel de Rosas 3910', 'Carrefour',
-- 'San Justo', 'Carrefour Argentina S.A.', 1, '1', 'https://carrefourar.vtexassets.com/assets/vtex/assets-builder/carrefourar.theme/74.0.0/logo/logo___8ebc4231614a7b41a4258354ce76e1e1.svg');
--
--INSERT INTO Supermercado (
--    distanciaNumero, distanciaDescripcion, banderaId, lat, lng, sucursalNombre,
--    id, sucursalTipo, provincia, direccion, banderaDescripcion, localidad,
--    comercioRazonSocial, comercioId, sucursalId, logoUrl
--) VALUES
--(2.2, 'A 2.2 km', 2, '-34.698071', '-58.559937', 'Coto Ciudad Evita',
-- '2', 'Hipermercado', 'Buenos Aires', 'Av. Cristianía 2700', 'Coto',
-- 'Ciudad Evita', 'Coto CICSA', 2, '2', 'https://www.coto.com.ar/images/logocotogde.png');
--
--INSERT INTO Supermercado (
--    distanciaNumero, distanciaDescripcion, banderaId, lat, lng, sucursalNombre,
--    id, sucursalTipo, provincia, direccion, banderaDescripcion, localidad,
--    comercioRazonSocial, comercioId, sucursalId, logoUrl
--) VALUES
--(3.5, 'A 3.5 km', 3, '-34.764971', '-58.561073', 'Jumbo Lomas del Mirador',
-- '3', 'Hipermercado', 'Buenos Aires', 'Av. Gral. San Martín 5100', 'Jumbo',
-- 'Lomas del Mirador', 'Cencosud S.A.', 3, '3', 'https://jumboargentinaio.vtexassets.com/assets/vtex/assets-builder/jumboargentinaio.store-theme/3.0.76/img/logo-jumbo___298a91c7745ef5319749159e7332eec5.svg');
--
--INSERT INTO Supermercado (
--    distanciaNumero, distanciaDescripcion, banderaId, lat, lng, sucursalNombre,
--    id, sucursalTipo, provincia, direccion, banderaDescripcion, localidad,
--    comercioRazonSocial, comercioId, sucursalId, logoUrl
--) VALUES
--(4.1, 'A 4.1 km', 4, '-34.688569', '-58.602478', 'Disco Ramos Mejía',
-- '4', 'Supermercado', 'Buenos Aires', 'Av. Rivadavia 14618', 'Disco',
-- 'Ramos Mejía', 'Disco S.A.', 4, '4', 'https://discoargentina.vtexassets.com/assets/vtex.file-manager-graphql/images/bd790034-1117-4263-90e5-04f3f5a27503___ab950355900559ccc0bc0cb131364561.png);
--
--INSERT INTO Supermercado (
--    distanciaNumero, distanciaDescripcion, banderaId, lat, lng, sucursalNombre,
--    id, sucursalTipo, provincia, direccion, banderaDescripcion, localidad,
--    comercioRazonSocial, comercioId, sucursalId, logoUrl
--) VALUES
--(3.8, 'A 3.8 km', 5, '-34.743797', '-58.567844', 'ChangoMas',
-- '5', 'Hipermercado', 'Buenos Aires', 'Av. Brig. Gral. Juan Manuel de Rosas 1800', 'ChangoMas',
-- 'Isidro Casanova', 'ChangoMas Argentina', 5, '5', 'https://masonlineprod.vtexassets.com/assets/vtex.file-manager-graphql/images/1f676005-1a66-4379-abe1-e8c39b539f10___3b7e04ce085fbfa38121c0692ad1dd9b.svg');
