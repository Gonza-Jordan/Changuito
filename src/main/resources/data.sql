--Usuarios
INSERT INTO Usuario(id, nombre, apellido, dni, direccion, email, contrasena)
VALUES(null, 'Jose', 'Lopez', 15012456, 'Florencio Varela 1903, San Justo, Provincia de Buenos Aires', 'jlopez@gmail.com','abcd1234');

--Productos
    --Cat. Almacen
INSERT INTO Producto (idProducto, nombre, precio, codigoBarras, categoria, subcategoria, urlImagen)
VALUES (1, 'Arroz Gallo Oro 1kg', 80.00, '7790020061045', 'Almacen', 'Arroz', 'img/producto/almacen/arroz_gallo_oro.jpg');

INSERT INTO Producto (idProducto, nombre, precio, codigoBarras, categoria, subcategoria, urlImagen)
VALUES (2, 'Fideos Don Vicente Spaghetti 500g', 45.90, '7790040072084', 'Almacen', 'Pastas', 'img/producto/almacen/fideos_don_vicente_spaghetti.jpg');

INSERT INTO Producto (idProducto, nombre, precio, codigoBarras, categoria, subcategoria, urlImagen)
VALUES (3, 'Aceite de Girasol Cocinero 1.5L', 180.00, '7792080000139', 'Almacen', 'Aceite', 'img/producto/almacen/aceite_cocinero.jpg');

INSERT INTO Producto (idProducto, nombre, precio, codigoBarras, categoria, subcategoria, urlImagen)
VALUES (4, 'Harina 0000 Pureza 1kg', 70.50, '7790040000013', 'Almacen', 'Harina', 'img/producto/almacen/harina_pureza.jpg');

INSERT INTO Producto (idProducto, nombre, precio, codigoBarras, categoria, subcategoria, urlImagen)
VALUES (5, 'Azúcar Ledesma 1kg', 60.00, '7790070130085', 'Almacen', 'Endulzantes', 'img/producto/almacen/azucar_ledesma.jpg');


    --Cat. Perfumeria
INSERT INTO Producto (idProducto, nombre, precio, codigoBarras, categoria, subcategoria, urlImagen)
VALUES (6, 'Shampoo Sedal Rizos Obedientes 650ml', 189.90, '7506306236160', 'Perfumeria', 'Shampoo', 'img/producto/perfumeria/shampoo_sedal_rizos.jpg');

INSERT INTO Producto (idProducto, nombre, precio, codigoBarras, categoria, subcategoria, urlImagen)
VALUES (7, 'Acondicionador Sedal Ceramidas 650ml', 189.90, '7506306236184', 'Perfumeria', 'Acondicionador', 'img/producto/perfumeria/acondicionador_sedal_ceramidas.jpg');

INSERT INTO Producto (idProducto, nombre, precio, codigoBarras, categoria, subcategoria, urlImagen)
VALUES (8, 'Desodorante Rexona Antibacterial 150ml', 99.90, '7506306210320', 'Perfumeria', 'Desodorantes', 'img/producto/perfumeria/desodorante_rexona_antibacterial.jpg');

INSERT INTO Producto (idProducto, nombre, precio, codigoBarras, categoria, subcategoria, urlImagen)
VALUES (9, 'Jabón Dove Go Fresh Pepino 4 unidades', 199.90, '7506306236733', 'Perfumeria', 'Jabones', 'img/producto/perfumeria/jabon_dove_go_fresh.jpg');

INSERT INTO Producto (idProducto, nombre, precio, codigoBarras, categoria, subcategoria, urlImagen)
VALUES (10, 'Crema Dental Colgate Total 12 90g', 99.90, '7506306236320', 'Perfumeria', 'Dentífricos', 'img/producto/perfumeria/crema_dental_colgate_total12.jpg');


    --Cat. Bebidas
INSERT INTO Producto (idProducto, nombre, precio, codigoBarras, categoria, subcategoria, urlImagen)
VALUES (11, 'Agua Mineral Villavicencio 1.5L', 60.00, '7790050322211', 'Bebidas', 'Agua', 'img/producto/bebidas/agua_villavicencio.jpg');

INSERT INTO Producto (idProducto, nombre, precio, codigoBarras, categoria, subcategoria, urlImagen)
VALUES (12, 'Gaseosa Coca-Cola Original 2.25L', 150.00, '7790040070066', 'Bebidas', 'Gaseosas', 'img/producto/bebidas/gaseosa_coca_cola.jpg');

INSERT INTO Producto (idProducto, nombre, precio, codigoBarras, categoria, subcategoria, urlImagen)
VALUES (13, 'Jugo Cepita Naranja 1L', 90.00, '7790070120130', 'Bebidas', 'Jugos', 'img/producto/bebidas/jugo_

INSERT INTO Producto (idProducto, nombre, precio, codigoBarras, categoria, subcategoria, urlImagen)
VALUES (14, 'Vino Malbec Rutini 750ml', 400.00, '7790167010209', 'Bebidas', 'Vinos', 'img/producto/bebidas/vino_rutini_malbec.jpg');

INSERT INTO Producto (idProducto, nombre, precio, codigoBarras, categoria, subcategoria, urlImagen)
VALUES (15, 'Cerveza Quilmes Stout 1L', 120.00, '7790060069501', 'Bebidas', 'Cervezas', 'img/producto/bebidas/cerveza_quilmes_stout.jpg');


    -- Cat. Verduleria
INSERT INTO Producto (idProducto, nombre, precio, codigoBarras, categoria, subcategoria, urlImagen)
VALUES (16, 'Tomate Redondo 1kg', 120.00, '7791234567001', 'Verduleria', 'Tomate', 'img/producto/verduleria/tomate_redondo.jpg');

INSERT INTO Producto (idProducto, nombre, precio, codigoBarras, categoria, subcategoria, urlImagen)
VALUES (17, 'Manzana Roja 1kg', 150.00, '7791234567002', 'Verduleria', 'Manzana', 'img/producto/verduleria/manzana_roja.jpg');

INSERT INTO Producto (idProducto, nombre, precio, codigoBarras, categoria, subcategoria, urlImagen)
VALUES (18, 'Lechuga Criolla 1 unidad', 80.00, '7791234567003', 'Verduleria', 'Lechuga', 'img/producto/verduleria/lechuga_criolla.jpg');

INSERT INTO Producto (idProducto, nombre, precio, codigoBarras, categoria, subcategoria, urlImagen)
VALUES (19, 'Zanahoria 1kg', 70.00, '7791234567004', 'Verduleria', 'Zanahoria', 'img/producto/verduleria/zanahoria.jpg');

INSERT INTO Producto (idProducto, nombre, precio, codigoBarras, categoria, subcategoria, urlImagen)
VALUES (20, 'Papa Negra 1kg', 50.00, '7791234567005', 'Verduleria', 'Papa', 'img/producto/verduleria/papa_negra.jpg');


    -- Cat. Limpieza
INSERT INTO Producto (idProducto, nombre, precio, codigoBarras, categoria, subcategoria, urlImagen)
VALUES (21, 'Lavandina Ayudín 1L', 60.00, '7791290031566', 'Limpieza', 'Lavandina', 'img/producto/limpieza/lavandina_ayudin.jpg');

INSERT INTO Producto (idProducto, nombre, precio, codigoBarras, categoria, subcategoria, urlImagen)
VALUES (22, 'Detergente Magistral Limón 750ml', 120.00, '7794000910323', 'Limpieza', 'Detergente', 'img/producto/limpieza/detergente_magistral_limon.jpg');

INSERT INTO Producto (idProducto, nombre, precio, codigoBarras, categoria, subcategoria, urlImagen)
VALUES (23, 'Esponja Scotch-Brite Multiuso', 25.00, '7793800002002', 'Limpieza', 'Esponjas', 'img/producto/limpieza/esponja_scotch_brite.jpg');

INSERT INTO Producto (idProducto, nombre, precio, codigoBarras, categoria, subcategoria, urlImagen)
VALUES (24, 'Trapo de Piso Vileda 1 unidad', 75.00, '7793884001224', 'Limpieza', 'Trapos', 'img/producto/limpieza/trapo_vileda.jpg');

INSERT INTO Producto (idProducto, nombre, precio, codigoBarras, categoria, subcategoria, urlImagen)
VALUES (25, 'Guantes de Látex Mapa Profesionales', 90.00, '7791234567010', 'Limpieza', 'Guantes', 'img/producto/limpieza/guantes_mapa.jpg');


    -- Cat. Lacteos
INSERT INTO Producto (idProducto, nombre, precio, codigoBarras, categoria, subcategoria, urlImagen)
VALUES (26, 'Leche La Serenísima Entera 1L', 70.00, '7790070410310', 'Lacteos', 'Leche', 'img/producto

INSERT INTO Producto (idProducto, nombre, precio, codigoBarras, categoria, subcategoria, urlImagen)
VALUES (27, 'Manteca Sancor 200g', 120.00, '7791335001063', 'Lacteos', 'Manteca', 'img/producto/lacteos/manteca_sancor.jpg');

INSERT INTO Producto (idProducto, nombre, precio, codigoBarras, categoria, subcategoria, urlImagen)
VALUES (28, 'Queso Cremoso La Paulina 500g', 180.00, '7790160000514', 'Lacteos', 'Queso', 'img/producto/lacteos/queso_cremoso_la_paulina.jpg');

INSERT INTO Producto (idProducto, nombre, precio, codigoBarras, categoria, subcategoria, urlImagen)
VALUES (29, 'Yogur Firme Serenito Frutilla 150g', 25.00, '7791335001063', 'Lacteos', 'Yogur', 'img/producto/lacteos/yogur_firme_serenito_frutilla.jpg');

INSERT INTO Producto (idProducto, nombre, precio, codigoBarras, categoria, subcategoria, urlImagen)
VALUES (30, 'Crema de Leche Ilolay 200ml', 60.00, '7793880000167', 'Lacteos', 'Crema', 'img/producto/lacteos/crema_de_leche_ilolay.jpg');

