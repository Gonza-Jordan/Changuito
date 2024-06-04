--Usuarios
INSERT INTO Usuario(id, nombre, apellido, dni, direccion, email, contrasena, admin)
VALUES (null, 'Jose', 'Lopez', 15012456, 'Florencio Varela 1903, San Justo, Provincia de Buenos Aires',
        'jlopez@gmail.com', 'abcd1234', false),
       (null, 'German', 'Perez', 15031258, 'Florencio Varela 1903, San Justo, Provincia de Buenos Aires',
        'gperez@gmail.com', 'abcd1234', true);

--Supermercados
INSERT INTO Supermercado (nombre, ubicacion, localidad, logo)
VALUES ('Carrefour', 'Avenida Mosconi 2871', 'San Justo',
        'https://carrefourar.vtexassets.com/assets/vtex/assets-builder/carrefourar.theme/74.0.0/logo/logo___8ebc4231614a7b41a4258354ce76e1e1.svg'),
       ('Coto', 'Avenida Brigadier Juan Manuel de Rosas 3990', 'San Justo',
        'https://logowik.com/content/uploads/images/supermercado-coto4935.logowik.com.webp'),
       ('Jumbo', 'Boulevard Buenos Aires 1001', 'Lomas del Mirador',
        'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQByoCb8u-rE8EFcTlcQbvHtcoa8HR-_d6_deVSuuF06w&s'),
       ('Dia', 'Avenida Juan Manuel de Rosas 11000', 'González Catán',
        'https://exportargentina.org.ar/companyimages/15441142122096.jpg'),
       ('Chango Mas', 'Ruta 3 Km 29', 'Isidro Casanova',
        'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT9wcqaCozuqN3MpF3B0hPbLaRxBPXfJTX97HlZQ8hRaA&s');

--Productos
    --Cat. Almacen
INSERT INTO Producto (nombre, codigoBarras, categoria, subcategoria, urlImagen)
VALUES
    --Cat. Almacen
    ('Arroz Gallo Oro 1kg', '11450502019', 'Almacen', 'Arroz', 'https://jumboargentina.vtexassets.com/arquivos/ids/803457-1200-auto?v=638379921950070000&width=1200&height=auto&aspect=true'),
    ('Arroz Dos Hermanos Largo Fino 1kg', '7790415010050', 'Almacen', 'Arroz', 'https://jumboargentina.vtexassets.com/arquivos/ids/637989-1200-auto?v=637544430793970000&width=1200&height=auto&aspect=true'),
    ('Arroz Gallo Oro Largo 1kg', '11450502019', 'Almacen', 'Arroz', 'https://jumboargentina.vtexassets.com/arquivos/ids/803457-1200-auto?v=638379921950070000&width=1200&height=auto&aspect=true'),
    ('Arroz Largo Fino Molinos Ala 1kg', '7790020000197', 'Almacen', 'Arroz', 'https://jumboargentina.vtexassets.com/arquivos/ids/625788-1200-auto?v=637528440071470000&width=1200&height=auto&aspect=true'),
    ('Arroz Lucchetti Largo Fino 1kg', '7790040015600', 'Almacen', 'Arroz', 'https://jumboargentina.vtexassets.com/arquivos/ids/818208-800-auto?v=638482955195770000&width=800&height=auto&aspect=true'),

    ('Fideos Don Vicente Tallarines 500g', '11460304020', 'Almacen', 'Pastas', 'https://jumboargentina.vtexassets.com/arquivos/ids/798895-1200-auto?v=638343636550800000&width=1200&height=auto&aspect=true'),
    ('Fideos Matarazzo Tirabuzón 500g', '7790070102366', 'Almacen', 'Pastas', 'https://jumboargentina.vtexassets.com/arquivos/ids/793499-1200-auto?v=638303241842570000&width=1200&height=auto&aspect=true'),
    ('Fideos Lucchetti Mostacholes 500g', '7790060111332', 'Almacen', 'Pastas', 'https://jumboargentina.vtexassets.com/arquivos/ids/793512-1200-auto?v=638303241882300000&width=1200&height=auto&aspect=true'),
    ('Fideos Favorita Spaghetti 500g', '7790012001215', 'Almacen', 'Pastas', 'https://jumboargentina.vtexassets.com/arquivos/ids/685850-800-auto?v=637787434515600000&width=800&height=auto&aspect=true'),
    ('Fideos Arcor Spaghetti 500g', '7790002001602', 'Almacen', 'Pastas', 'https://jumboargentina.vtexassets.com/arquivos/ids/624113-800-auto?v=637505107327000000&width=800&height=auto&aspect=true'),

    ('Aceite de Girasol Cocinero 1.5L', '12610905011', 'Almacen', 'Aceite', 'https://jumboargentina.vtexassets.com/arquivos/ids/614743-1200-auto?v=637409203109900000&width=1200&height=auto&aspect=true'),
    ('Aceite de Girasol Natura 1.5L', '7790070101356', 'Almacen', 'Aceite', 'https://jumboargentina.vtexassets.com/arquivos/ids/427751-1200-auto?v=636495154762100000&width=1200&height=auto&aspect=true'),
    ('Aceite de Maíz Mazola 1L', '7790070100106', 'Almacen', 'Aceite', 'https://jumboargentina.vtexassets.com/arquivos/ids/194799-1200-auto?v=636383557391870000&width=1200&height=auto&aspect=true'),
    ('Aceite Natura Blend 1.5L', '7790070100205', 'Almacen', 'Aceite', 'https://jumboargentina.vtexassets.com/arquivos/ids/208072-800-auto?v=636383711897100000&width=800&height=auto&aspect=true'),
    ('Aceite Cañuelas 1.5L', '7790012001456', 'Almacen', 'Aceite', 'https://jumboargentina.vtexassets.com/arquivos/ids/427642-800-auto?v=636495153112630000&width=800&height=auto&aspect=true'),

    ('Harina 0000 Pureza 1kg', '11410142005', 'Almacen', 'Harina', 'https://jumboargentina.vtexassets.com/arquivos/ids/644481-1200-auto?v=637572504306700000&width=1200&height=auto&aspect=true'),
    ('Harina Integral Favorita 1kg', '7790070411376', 'Almacen', 'Harina', 'https://jumboargentina.vtexassets.com/arquivos/ids/803442-1200-auto?v=638379921894370000&width=1200&height=auto&aspect=true'),
    ('Harina 000 Morixe 1kg', '7790012001389', 'Almacen', 'Harina', 'https://ardiaprod.vtexassets.com/arquivos/ids/291081-800-auto?v=638437873870900000&width=800&height=auto&aspect=true'),
    ('Harina Blancaflor 1kg', '7790070411206', 'Almacen', 'Harina', 'https://jumboargentina.vtexassets.com/arquivos/ids/822721-800-auto?v=638508241322270000&width=800&height=auto&aspect=true'),

    ('Azúcar Molida Ledesma 1kg', '11270101016', 'Almacen', 'Endulzantes', 'https://jumboargentina.vtexassets.com/arquivos/ids/703064-1200-auto?v=637891329664700000&width=1200&height=auto&aspect=true'),
    ('Azucar Blanca Domino 1kg', '7790040098328', 'Almacen', 'Endulzantes', 'https://jumboargentina.vtexassets.com/arquivos/ids/173691-800-auto?v=636383168620300000&width=800&height=auto&aspect=true'),
    ('Miel Pura Flores 500g', '7790217000286', 'Almacen', 'Endulzantes', 'https://static.cotodigital3.com.ar/sitios/fotos/full/00498500/00498596.jpg?3.0.170a'),
    ('Edulcorante Hileret Light', '7790409000624', 'Almacen', 'Endulzantes', 'https://d3340tyzmtlo4u.cloudfront.net/users/864/images/thumbnails/300/300/detailed/13/Hileret_Edulcorante_L%C3%ADquido_Sweet,_400_ml.webp'),
    ('Stevia Pure Via Liquido 80ml', '7790200000364', 'Almacen', 'Endulzantes', 'https://carrefourar.vtexassets.com/arquivos/ids/318095-800-auto?v=638180311874530000&width=800&height=auto&aspect=true'),

    --Cat. Perfumeria
    ('Shampoo Sedal Rizos Definidos 650ml', '13710375002', 'Perfumeria', 'Shampoo', 'https://jumboargentina.vtexassets.com/arquivos/ids/766775-1200-auto?v=638108302089300000&width=1200&height=auto&aspect=true'),
    ('Acondicionador Sedal Ceramidas 650ml', '13720863004', 'Perfumeria', 'Acondicionador', 'https://jumboargentina.vtexassets.com/arquivos/ids/787029-1200-auto?v=638240818051470000&width=1200&height=auto&aspect=true'),
    ('Desodorante Rexona Antibacterial 150ml', '13420110034', 'Perfumeria', 'Desodorantes', 'https://jumboargentina.vtexassets.com/arquivos/ids/802194-1200-auto?v=638375603688170000&width=1200&height=auto&aspect=true'),
    ('Jabón Dove Go Original 90g', '13110109050', 'Perfumeria', 'Jabones', 'https://jumboargentina.vtexassets.com/arquivos/ids/806393-1200-auto?v=638403357956070000&width=1200&height=auto&aspect=true'),
    ('Crema Dental Colgate Total 12 90g', '13220102208', 'Perfumeria', 'Dentifricos', 'https://jumboargentina.vtexassets.com/arquivos/ids/813235-1200-auto?v=638441267527100000&width=1200&height=auto&aspect=true'),
    ('Shampoo Pantene Pro-V 400ml', '13710406001', 'Perfumeria', 'Shampoo', 'https://via.placeholder.com/150'),
    ('Acondicionador Dove Reconstrucción Completa 400ml', '13720589005', 'Perfumeria', 'Acondicionador', 'https://via.placeholder.com/150'),

    --Cat. Bebidas
    ('Agua Mineral Glaciar Sin Gas Bajo Sodio 1.5L', '12120102001', 'Bebidas', 'Agua', 'https://jumboargentina.vtexassets.com/arquivos/ids/545277-1200-auto?v=637014798216800000&width=1200&height=auto&aspect=true'),
    ('Gaseosa Coca-Cola Original 2.25L', '12110101009', 'Bebidas', 'Gaseosas', 'https://jumboargentina.vtexassets.com/arquivos/ids/782825-1200-auto?v=638206689776800000&width=1200&height=auto&aspect=true'),
    ('Jugo Cepita Fresh Naranja 1.5L', '12130595001', 'Bebidas', 'Jugos', 'https://jumboargentina.vtexassets.com/arquivos/ids/801011-1200-auto?v=638367069703130000&width=1200&height=auto&aspect=true'),
    ('Vino tinto Elementos Malbec 750Cc', '12262130001', 'Bebidas', 'Vinos', 'https://jumboargentina.vtexassets.com/arquivos/ids/581089-1200-auto?v=637225176402330000&width=1200&height=auto&aspect=true'),
    ('Cerveza Quilmes Clasica 1L', '12440106063', 'Bebidas', 'Cervezas', 'https://jumboargentina.vtexassets.com/arquivos/ids/433499-1200-auto?v=636517616933330000&width=1200&height=auto&aspect=true'),

    -- Cat. Verduleria
    ('Tomate Redondo Grande 1kg', '23210315001', 'Verduleria', 'Tomate', 'https://jumboargentina.vtexassets.com/arquivos/ids/339429-1200-auto?v=636393043636870000&width=1200&height=auto&aspect=true'),
    ('Manzana Roja 1kg', '23110102025', 'Verduleria', 'Manzana', 'https://jumboargentina.vtexassets.com/arquivos/ids/472481-1200-auto?v=636694698370130000&width=1200&height=auto&aspect=true'),
    ('Lechuga Capuchina 1Kg', '23210120001', 'Verduleria', 'Lechuga', 'https://jumboargentina.vtexassets.com/arquivos/ids/450976-1200-auto?v=636577194077270000&width=1200&height=auto&aspect=true'),
    ('Zanahoria 1kg', '23210512002', 'Verduleria', 'Zanahoria', 'https://jumboargentina.vtexassets.com/arquivos/ids/472800-1200-auto?v=636695562251270000&width=1200&height=auto&aspect=true'),
    ('Papa Negra 1kg', '23210509005', 'Verduleria', 'Papa', 'https://jumboargentina.vtexassets.com/arquivos/ids/449110-1200-auto?v=636567690053330000&width=1200&height=auto&aspect=true'),

    -- Cat. Limpieza
    ('Lavandina Ayudín 1L', '14160501064', 'Limpieza', 'Lavandina', 'https://jumboargentina.vtexassets.com/arquivos/ids/678535-1200-auto?v=637739049922700000&width=1200&height=auto&aspect=true'),
    ('Detergente Líquido Magistral Limón 750ml', '14210196003', 'Limpieza', 'Detergente', 'https://jumboargentina.vtexassets.com/arquivos/ids/781293-1200-auto?v=638194917774670000&width=1200&height=auto&aspect=true'),
    ('Esponja Clasica Scotch-Brite', '43140757003', 'Limpieza', 'Esponjas', 'https://jumboargentina.vtexassets.com/arquivos/ids/529332-1200-auto?v=636898152780870000&width=1200&height=auto&aspect=true'),
    ('Trapo de Piso Nido De Abeja 1 unidad', '43140174001', 'Limpieza', 'Trapos', 'https://jumboargentina.vtexassets.com/arquivos/ids/794169-1200-auto?v=638307562527270000&width=1200&height=auto&aspect=true'),
    ('Guantes de Látex Mapa 8 1/2', '43150160004', 'Limpieza', 'Guantes', 'https://jumboargentina.vtexassets.com/arquivos/ids/527864-1200-auto?v=636890370760170000&width=1200&height=auto&aspect=true'),

    -- Cat. Lacteos
    ('Leche Descremada La Serenísima 1L', '21110301058', 'Lacteos', 'Leche', 'https://jumboargentina.vtexassets.com/arquivos/ids/760437-1200-auto?v=638049118084400000&width=1200&height=auto&aspect=true'),
    ('Manteca Tonadita 200g', '21270184001', 'Lacteos', 'Manteca', 'https://jumboargentina.vtexassets.com/arquivos/ids/176504-1200-auto?v=636383331571730000&width=1200&height=auto&aspect=true'),
    ('Queso Parmesano La Paulina 1Kg', '24450703003', 'Lacteos', 'Quesos', 'https://jumboargentina.vtexassets.com/arquivos/ids/596261-1200-auto?v=637315026086830000&width=1200&height=auto&aspect=true'),
    ('Yogur Yogurisimo Natural 300g', '21220166004', 'Lacteos', 'Yogures', 'https://jumboargentina.vtexassets.com/arquivos/ids/790645-1200-auto?v=638277969692900000&width=1200&height=auto&aspect=true'),
    ('Crema Doble Tregar 350g', '21210150006', 'Lacteos', 'Crema', 'https://jumboargentina.vtexassets.com/arquivos/ids/774486-1200-auto?v=638150854086930000&width=1200&height=auto&aspect=true');


INSERT INTO SupermercadoProducto (supermercado_id, producto_id, precio, descuento)
VALUES
    (1, 1, 3750, 0.95),
    (2, 2, 3600, null),
    (3, 3, 4500, 0.95),
    (4, 4, 3650, 0.95),
    (5, 5, 3800, null),
    (1, 6, 2000, null),
    (2, 7, 2100, null),
    (3, 8, 1900, null),
    (4, 9, 1800, null),
    (5, 10, 1850, null),
    (1, 11, 2100, null),
    (2, 12, 2400, null),
    (3, 13, 2500, null),
    (4, 14, 2300, null),
    (5, 15, 3500, null),
    (1, 16, 900, null),
    (2, 17, 950, null),
    (3, 18, 920, null),
    (4, 19, 1050, null),
    (5, 20, 1308, null),
    (1, 21, 1350, null),
    (2, 22, 8000, null),
    (3, 23, 1600, null),
    (4, 24, 950, null),
    (5, 25, 5150, null),
    (1, 26, 5200, null),
    (2, 27, 2500, null),
    (3, 28, 1300, null),
    (4, 29, 3315, null),
    (5, 30, 4500, null),
    (1, 31, 4600, null),
    (2, 32, 1554, null),
    (3, 33, 3100, null),
    (4, 34, 1485, null),
    (5, 35, 3900, null),
    (1, 36, 1783, null),
    (2, 37, 4499, null),
    (3, 38, 2299, null),
    (4, 39, 3699, null),
    (5, 40, 1199, null),
    (1, 41, 599, null),
    (2, 42, 699, null),
    (3, 43, 3550, null),
    (4, 44, 562, null),
    (5, 45, 2775, null),
    (1, 46, 3787, null),
    (2, 47, 1362, null),
    (3, 48, 2250, null),
    (4, 49, 12999, null),
    (5, 50, 1700, null),
    (1, 51, 2080, null),

(2, 1, 3500, null);